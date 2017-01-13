package com.briarshore.rule;

import java.util.Comparator;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.TreeMapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Class: CustomTreeMapConverter
 */
public class CustomTreeMapConverter extends TreeMapConverter {
    private static final transient Logger log = LoggerFactory.getLogger(CustomTreeMapConverter.class);

    public CustomTreeMapConverter(final Mapper mapper) {
        super(mapper);
        log.debug("CustomTreeMapConverter() constructor");
    }

    @Override
    protected void marshalComparator(final Comparator comparator, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        log.debug("marshalComparator(): {}", context.toString());
    }

    @Override
    protected Comparator unmarshalComparator(final HierarchicalStreamReader reader, final UnmarshallingContext context, final TreeMap result) {
        log.debug("unmarshalComparator()");
        return null;
    }
}
