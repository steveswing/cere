package com.briarshore.rule;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Nullable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

/**
 * Class: RuleContainerBuilderFactory
 */
public class RuleContainerBuilderFactory<T> {
    private static final Log log = LogFactory.getLog(RuleContainerBuilderFactory.class);
    private static final Map<String, Class<? extends RuleContainerBuilder<?>>> builderClasses = initializeBuilderClasses();
    private static final Map<String, RuleContainerBuilder> buildersByString = new TreeMap<>();
    private static final Map<RuleType, RuleContainerBuilder> buildersByRuleType = new TreeMap<>();

    public static <T> Map<String, Class<? extends RuleContainerBuilder<?>>> initializeBuilderClasses() {
        final Map<String, Class<? extends RuleContainerBuilder<?>>> result = Maps.newTreeMap();
        try {
            final Properties ruleBuilderProperties = new Properties();
            ruleBuilderProperties.load(RuleContainerBuilderFactory.class.getResourceAsStream("/rule-builders.properties"));
            final Map<String, String> ruleBuilderClassNames = Maps.fromProperties(ruleBuilderProperties);
            result.putAll(Maps.filterValues(Maps.transformValues(ruleBuilderClassNames, new Function<String, Class<? extends RuleContainerBuilder<T>>>() {
                @Nullable
                @Override
                public Class<? extends RuleContainerBuilder<T>> apply(@Nullable final String s) {
                    try {
                        return extractClassFromName(s);
                    } catch (ClassNotFoundException e) {
                        log.error("Invalid class name entry in rule-builders.properties", e);
                        return null;
                    }
                }
            }), new Predicate<Class<? extends RuleContainerBuilder<T>>>() {
                @Override
                public boolean apply(@Nullable final Class<? extends RuleContainerBuilder<T>> c) {
                    return null != c;
                }
            }));
        } catch (IOException e) {
            log.error("Error reading rule-builders.properties", e);
        }
        return result;
    }

    private static <T> Class<? extends RuleContainerBuilder<T>> extractClassFromName(final String className) throws ClassNotFoundException {
        return (Class<? extends RuleContainerBuilder<T>>)Class.forName(className);
    }

    public static <T> RuleContainerBuilder<T> getRuleContainerBuilder(final String name, final Table<String, String, String> values)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        final Constructor<? extends RuleContainerBuilder<T>> constructor = extractConstructor(name);
        final RuleContainerBuilder<T> builder = null == constructor ? null : constructor.newInstance(name, values);
        if (null != builder) {
            final RuleType ruleType = builder.getRuleType();
            if (null != ruleType) {
                buildersByString.put(ruleType.getName(), builder);
                buildersByRuleType.put(ruleType, builder);
            }
        }
        return builder;
    }

    protected static <T> Constructor<? extends RuleContainerBuilder<T>> extractConstructor(final String name) throws NoSuchMethodException {
        final Class<? extends RuleContainerBuilder<?>> cls = builderClasses.get(name);
        return null == cls ? null : (Constructor<? extends RuleContainerBuilder<T>>)cls.getConstructor(String.class, Table.class);
    }

    public RuleContainerBuilder<T> findRuleContainerBuilder(final RuleType ruleType) {
        return buildersByRuleType.get(ruleType);
    }
}
