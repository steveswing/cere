package com.briarshore.rule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.dozer.DozerBeanMapper;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class RuntimeRuleLoader<T> {
    private static final String RULE_TYPE_XML = "/RuleType.xml";
    private static final Logger log = LoggerFactory.getLogger(RuntimeRuleLoader.class);
    protected transient DozerBeanMapper dozerBeanMapper;
    private RuleEngine<T> engine;

    public RuntimeRuleLoader(DozerBeanMapper dozerBeanMapper, final RuleEngine<T> engine) {
        this.dozerBeanMapper = dozerBeanMapper;
        this.engine = engine;
    }

    public void initialize() {
        final XStream xStream = getXStream();
        final Collection<RuleType> ruleTypes = (Collection<RuleType>)xStream.fromXML(getClass().getResourceAsStream(RULE_TYPE_XML));
        if (null != ruleTypes && !ruleTypes.isEmpty()) {
            ruleTypes.stream().flatMap(new Function<RuleType, Stream<Rule<T>>>() {
                @Override
                public Stream<Rule<T>> apply(final RuleType rt) {
                    try {
                        return ((Collection<Rule<T>>)xStream.fromXML(getClass().getResourceAsStream("/" + rt.getName() + ".xml"))).stream();
                    } catch (Exception e) {
                        log.error("error reading /%s.xml. No rules loaded for this rule type.", e, rt.getName());
                    }
                    return Stream.empty();
                }
            }).forEach(r -> engine.add(r.getRuleType(), r));
        } else {
            throw new RuntimeException(String.format("Failed to find %s to load rule types.", RULE_TYPE_XML));
        }
    }

    protected XStream getXStream() {
        final XStream xstream = new XStream(new CustomPureJavaReflectionProvider(), new Xpp3Driver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.autodetectAnnotations(true);
        xstream.registerConverter(new CustomTreeMapConverter(xstream.getMapper()), XStream.PRIORITY_VERY_HIGH);
        xstream.registerConverter(new CustomTreeSetConverter(xstream.getMapper()), XStream.PRIORITY_VERY_HIGH);
        xstream.registerConverter(new XStreamDozerBeanMapperConverter(dozerBeanMapper));
        xstream.registerConverter(new XStreamLevelConverter());

        final Reflections reflections = new Reflections(getClass().getPackage().getName());
        final Set<Class<? extends Converter>> converters = reflections.getSubTypesOf(Converter.class);
        converters.remove(XStreamRuleTypeConverter.class);
        converters.remove(XStreamDozerBeanMapperConverter.class);
        for (final Class<? extends Converter> cls : converters) {
            try {
                xstream.registerConverter(cls.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("problem creating converter class \"{}\" to register.", cls.getSimpleName(), e);
            }
        }

        final Set<Class<? extends Serializable>> serializables = reflections.getSubTypesOf(Serializable.class);
        for (final Class<? extends Serializable> cls : serializables) {
            xstream.alias(cls.getSimpleName(), cls);
        }

        xstream.useAttributeFor(String.class);
        xstream.useAttributeFor(int.class);
        xstream.useAttributeFor(long.class);
        xstream.useAttributeFor(boolean.class);
        registerAliases(xstream);
        xstream.processAnnotations(getClass());
        xstream.processAnnotations(RuleImpl.class);

        return xstream;
    }

    public RuleEngine<T> read(final InputStream ruleSource) throws IOException {
        if (null != ruleSource) {
            getXStream().fromXML(ruleSource, engine);
        }
        return engine;
    }

    public void write(final File ruleTarget) throws IOException {
        if (null != engine) {
            final FileWriter fileWriter = new FileWriter(ruleTarget);
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            try {
                final XStream xstream = getXStream();
                bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
                bufferedWriter.write(xstream.toXML(engine));
            } finally {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        }
    }

    protected void registerAliases(final XStream xstream) {
        xstream.aliasType(LocalTime.class.getSimpleName(), LocalTime.class);
        xstream.aliasType(LocalDate.class.getSimpleName(), LocalDate.class);
        xstream.aliasType(LocalDateTime.class.getSimpleName(), LocalDateTime.class);
        xstream.aliasType("dozer-mapper", DozerBeanMapper.class);
        final XStreamRuleTypeConverter ruleTypeConverter = new XStreamRuleTypeConverter();
        xstream.registerLocalConverter(RuleContainer.class, "ruleType", ruleTypeConverter);
        xstream.registerLocalConverter(RuleImpl.class, "ruleType", ruleTypeConverter);
        xstream.useAttributeFor(RuleType.class, "id");
        xstream.useAttributeFor(RuleType.class, "name");
        xstream.useAttributeFor(RuleType.class, "description");
        xstream.useAttributeFor(RuleType.class, "priority");
    }
}
