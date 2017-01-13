package com.briarshore.rule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

/**
 * Class: RuleContainerBuilder
 */
public abstract class RuleContainerBuilder<T> {
    public static final List<String> keysHeaderRows = Arrays.asList("0", "1", "2");
    protected final String name;
    protected final Table<String, String, String> values;
    protected RuleType ruleType;
    private int rowCount = 0;

    public RuleContainerBuilder(final String name, final Table<String, String, String> values) {
        this.name = name;
        this.values = values;
        this.ruleType = RuleType.findByName(name);
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(final RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public abstract int getRuleId(Map<String, String> columns);

    public abstract String getDescription(Map<String, String> columns);

    public String makeRuleName(final int ruleId) {
        return String.format("%s-%03d", null == ruleType ? name : ruleType.getName(), ruleId);
    }

    protected Maps.EntryTransformer<String, Map<String, String>, Rule<T>> getRuleTransformer() {
        return (rowNbr, columns) -> {
            final int ruleId = getRuleId(columns);
            return 0 < ruleId ? RuleFactory.getRule(ruleId, ruleType, makeRuleName(ruleId), getDescription(columns), getConditions(columns), getActions(columns)) : null;
        };
    }

    public RuleContainer<T> build() {
        values.rowKeySet().removeAll(keysHeaderRows);
        rowCount = Math.max(rowCount, values.rowMap().size());
        return makeRuleContainer(Maps.transformEntries(values.rowMap(), getRuleTransformer()));
    }

    protected RuleContainer<T> makeRuleContainer(final Map<String, Rule<T>> rulesById) {
        final RuleContainer<T> result = new RuleContainer<T>(ruleType, name);
        result.addAll(rulesById.values());
        if (rowCount != result.getRuleCount()) {
            throw new IllegalStateException(String.format("Expected %d rules (from %s worksheet rows) but only created %d.", rowCount, name, result.getRuleCount()));
        }
        return result;
    }

    public abstract Collection<Condition<T>> getConditions(final Map<String, String> columns);

    protected void add(final List<Condition<T>> result, final Condition<T> condition) {
        if (null != condition) {
            result.add(condition);
        }
    }

    protected void add(final List<Action<T>> result, final Action<T> action) {
        if (null != action) {
            result.add(action);
        }
    }

    public abstract Collection<Action<T>> getActions(final Map<String, String> columns);
}
