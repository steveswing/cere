package com.briarshore.rule;

import java.io.IOException;
import java.io.InputStream;

import org.dozer.DozerBeanMapper;

public class DiagnosticRuleEngineFactory<T> extends RuleEngineFactory<T> {
    @Override
    public RuleEngine<T> getRuleEngine(final DozerBeanMapper dozerBeanMapper, final InputStream inputStream) throws IOException {
        super.ruleEngine = new RuntimeRuleLoader<T>(dozerBeanMapper, new DiagnosticRuleEngine<T>()).read(inputStream);
        return super.ruleEngine;
    }
}
