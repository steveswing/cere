package com.briarshore.rule;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("rules")
public class DiagnosticRuleEngine<T> extends DefaultRuleEngine<T> {
    private final ConcurrentMap<T, DiagnosticsResult<T>> diagnosticsResults = new ConcurrentHashMap<>();

    @Override
    public synchronized void add(final RuleType type, final Rule<T> rule) {
        super.add(type, new DiagnosticRuleImpl<>(rule, diagnosticsResults));
    }
}
