package com.briarshore.rule;

import java.io.IOException;
import java.io.InputStream;

import org.dozer.DozerBeanMapper;

/**
 * Class: RuleEngineFactory
 */
public class RuleEngineFactory<T> {
    protected static RuleEngineFactory ruleEngineFactory;
    protected RuleEngine<T> ruleEngine;

    public static <T> RuleEngineFactory<T> getInstance() {
        if (null == ruleEngineFactory) {
            ruleEngineFactory = new RuleEngineFactory<T>();
        }
        return ruleEngineFactory;
    }

    public RuleEngine<T> getRuleEngine(final DozerBeanMapper dozerBeanMapper) throws IOException {
        return getRuleEngine(dozerBeanMapper, null);
    }

    public RuleEngine<T> getRuleEngine(final DozerBeanMapper dozerBeanMapper, final InputStream inputStream) throws IOException {
        if (null == ruleEngine) {
            ruleEngine = new RuntimeRuleLoader<>(dozerBeanMapper, new DefaultRuleEngine<T>()).read(inputStream);
        }
        return ruleEngine;
    }

    public void setRuleEngine(final RuleEngine<T> ruleEngine) {
        this.ruleEngine = ruleEngine;
    }
}
