package com.briarshore.rule;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.briarshore.util.Strings;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Class: XStreamLocalTimeConverter
 */
public class XStreamLocalTimeConverter implements Converter {

    public static final String ATTRIBUTE_NAME = "value";

    @Override
    public boolean canConvert(final Class cls) {
        return null != cls && LocalTime.class.isAssignableFrom(cls);
    }

    public String toString(final Object o) {
        return o instanceof LocalTime ? DateTimeFormatter.ISO_LOCAL_TIME.format((LocalTime)o) : Strings.EMPTY;
    }

    public Object fromString(final String s) {
        return Strings.isBlank(s) ? null : LocalTime.parse(s);
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
