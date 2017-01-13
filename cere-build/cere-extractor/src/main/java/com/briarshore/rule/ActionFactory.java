package com.briarshore.rule;

import org.apache.commons.lang3.BooleanUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class: ActionFactory
 */
public class ActionFactory<T> {
    public static final Logger log = LoggerFactory.getLogger(ActionFactory.class);
    private static DozerBeanMapper dozerBeanMapper = null;

    private ActionFactory() {
    }

    public static DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public static void setDozerBeanMapper(final DozerBeanMapper dozerBeanMapper) {
        ActionFactory.dozerBeanMapper = dozerBeanMapper;
    }

    public static boolean asRequired(final String s) {
        return BooleanUtils.toBoolean(s);
    }
}
