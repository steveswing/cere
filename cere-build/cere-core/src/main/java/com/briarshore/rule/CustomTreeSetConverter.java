package com.briarshore.rule;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Class: CustomTreeSetConverter
 */
public class CustomTreeSetConverter extends CollectionConverter {
    private static final transient Logger logger = LoggerFactory.getLogger(CustomTreeSetConverter.class);

    public CustomTreeSetConverter(final Mapper mapper) {
        super(mapper, TreeSet.class);
        logger.debug("CustomTreeSetConverter() constructor");
    }

    @Override
    public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        super.marshal(source, writer, context);
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        return super.unmarshal(reader, context);
    }
}
