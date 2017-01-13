package com.briarshore.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;
import com.google.common.collect.Table;

import static org.apache.poi.ss.util.CellReference.convertNumToColString;

/**
 * Class: StartDateRuleBuilder
 */
public class SampleRuleContainerBuilder<T> extends RuleContainerBuilder<T> {
    private static int index = 0;

    private static final String COLUMN_INDEX_A_RULE_ID = convertNumToColString(index++);
    private static final String COLUMN_INDEX_B_DESCRIPTION = convertNumToColString(index++);
    private static final String COLUMN_INDEX_C_EFFECTIVE_DATE = convertNumToColString(index++);
    private static final String COLUMN_INDEX_D_EXPIRATION_DATE = convertNumToColString(index++);

    public SampleRuleContainerBuilder(final String name, final Table<String, String, String> values) {
        super(name, values);
    }

    @Override
    public int getRuleId(Map<String, String> columns) {
        return Integer.valueOf(StringUtils.defaultIfBlank(columns.get(COLUMN_INDEX_A_RULE_ID), "-1"));
    }

    @Override
    public String getDescription(final Map<String, String> columns) {
        return StringUtils.trimToEmpty(columns.get(COLUMN_INDEX_B_DESCRIPTION));
    }

    @Override
    public Collection<Condition<T>> getConditions(final Map<String, String> columns) {
        final List<Condition<T>> result = new ArrayList<>();
        add(result, ConditionFactory
                .effective(Boolean.TRUE, ConditionFactory.asDateTime(columns.get(COLUMN_INDEX_C_EFFECTIVE_DATE)), ConditionFactory.asDateTime(columns.get(COLUMN_INDEX_D_EXPIRATION_DATE))));
        return result;
    }

    @Override
    public Collection<Action<T>> getActions(final Map<String, String> columns) {
        final List<Action<T>> result = new ArrayList<>();
        return result;
    }
}
