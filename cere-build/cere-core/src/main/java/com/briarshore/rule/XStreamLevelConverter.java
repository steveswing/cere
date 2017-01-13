package com.briarshore.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;

import com.briarshore.util.Strings;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * Class: XStreamLevelConverter
 */
public class XStreamLevelConverter implements Converter {
    public static final String ATTRIBUTE_NAME = "value";
    private static final transient Map<Level, String> levelNamesByLevel = initializeLevelNamesByLevel();
    private static final transient Map<String, Level> levelsByLevelName = initializeLevelsByLevelName();

    protected static Map<Level, String> initializeLevelNamesByLevel() {
        final Map<Level, String> result = new HashMap<>();
        result.put(Level.ALL, Level.ALL.toString());
        result.put(Level.TRACE, Level.TRACE.toString());
        result.put(Level.DEBUG, Level.DEBUG.toString());
        result.put(Level.INFO, Level.INFO.toString());
        result.put(Level.WARN, Level.WARN.toString());
        result.put(Level.ERROR, Level.ERROR.toString());
        result.put(Level.FATAL, Level.FATAL.toString());
        result.put(Level.OFF, Level.OFF.toString());
        return result;
    }

    protected static Map<String, Level> initializeLevelsByLevelName() {
        final Map<String, Level> result = new HashMap<>();
        levelNamesByLevel.entrySet().forEach(e -> result.put(e.getValue(), e.getKey()));
        return result;
    }

    @Override
    public boolean canConvert(final Class type) {
        return Level.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        writer.addAttribute(ATTRIBUTE_NAME, toString(source));
    }

    private String toString(final Object o) {
        return o instanceof Level ? o.toString() : Strings.EMPTY;
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        return levelsByLevelName.get(reader.getAttribute(ATTRIBUTE_NAME));
    }
}
