package com.briarshore.rule;

import org.dozer.DozerBeanMapper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Class: XStreamDozerBeanMapperConverter
 */
public class XStreamDozerBeanMapperConverter implements Converter {
    public static final String ATTRIBUTE_NAME = "value";
    private static DozerBeanMapper dozerBeanMapper;

    public XStreamDozerBeanMapperConverter() {
    }

    public XStreamDozerBeanMapperConverter(final DozerBeanMapper dozerBeanMapper) {
        setDozerBeanMapper(dozerBeanMapper);
    }

    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(final DozerBeanMapper dozerBeanMapper) {
        if (null == XStreamDozerBeanMapperConverter.dozerBeanMapper && null != dozerBeanMapper) {
            XStreamDozerBeanMapperConverter.dozerBeanMapper = dozerBeanMapper;
        }
    }

    public String toString(final Object o) {
        return "defaultMapper";
    }

    public Object fromString(final String s) {
        return dozerBeanMapper;
    }

    @Override
    public boolean canConvert(final Class cls) {
        return null != cls && DozerBeanMapper.class.isAssignableFrom(cls);
    }

    @Override
    public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        writer.addAttribute(ATTRIBUTE_NAME, toString(source));
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        return fromString(reader.getAttribute(ATTRIBUTE_NAME));
    }
}
