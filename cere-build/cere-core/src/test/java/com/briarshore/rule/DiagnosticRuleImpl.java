package com.briarshore.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;

public class DiagnosticRuleImpl<T> extends RuleImpl<T> {
    private static final long serialVersionUID = 1L;
    private Rule<T> rule;
    private Map<T, DiagnosticsResult<T>> diagnosticsResults;

    public DiagnosticRuleImpl() {
        super();
    }

    public DiagnosticRuleImpl(final Rule<T> rule, final Map<T, DiagnosticsResult<T>> diagnosticsResults) {
        this.rule = rule;
        this.diagnosticsResults = diagnosticsResults;
    }

    @Override
    public boolean fire(final T t) {
        if (null != rule) {
            final DiagnosticsResult<T> diagnosticsResult = diagnosticsResults.computeIfAbsent(t, DiagnosticsResult::new);
            rule.getConditions().forEach(c -> diagnosticsResult.recordResult(rule, c.test(t), c));
            return rule.fire(t);
        }
        return false;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DiagnosticRuleImpl that = (DiagnosticRuleImpl)o;

        if (rule != null ? !rule.equals(that.rule) : that.rule != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (rule != null ? rule.hashCode() : 0);
    }

    @Override
    public Collection<Condition<T>> getConditions() {
        return null == rule ? new ArrayList<>() : rule.getConditions();
    }

    @Override
    public Collection<Action<T>> getActions() {
        return null == rule ? new ArrayList<>() : rule.getActions();
    }

    @Override
    public String toString() {
        return null == rule ? "" : rule.toString();
    }
}
