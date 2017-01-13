package com.briarshore.rule;

import com.briarshore.util.Strings;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XStreamRuleTypeConverter implements Converter {
    public static final String ATTRIBUTE_NAME = "name";

    public Object fromString(final String s) {
        return RuleType.findByName(s);
    }

    @Override
    public boolean canConvert(final Class cls) {
        return null != cls && RuleType.class.isAssignableFrom(cls);
    }

    public String toString(final Object o) {
        return o instanceof RuleType ? ((RuleType)o).getName() : Strings.EMPTY;
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
