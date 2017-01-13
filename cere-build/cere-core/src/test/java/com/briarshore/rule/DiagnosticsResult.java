package com.briarshore.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.briarshore.rule.conditions.Condition;

public class DiagnosticsResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Rule<T>, Map<Boolean, Collection<Condition<T>>>> results = new LinkedHashMap<>();
    private T subject;

    public DiagnosticsResult() {
    }

    public DiagnosticsResult(final T subject) {
        this.subject = subject;
    }

    public T getSubject() {
        return subject;
    }

    public Map<Rule<T>, Map<Boolean, Collection<Condition<T>>>> getResults() {
        return Collections.unmodifiableMap(results);
    }

    public void recordResult(final Rule<T> rule, final boolean diagnosticKey, final Condition<T> condition) {
        results.computeIfAbsent(rule, k1 -> new LinkedHashMap<>()).computeIfAbsent(diagnosticKey, k -> new ArrayList<>()).add(condition);
    }
}
