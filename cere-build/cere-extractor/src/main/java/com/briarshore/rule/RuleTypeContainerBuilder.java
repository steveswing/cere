package com.briarshore.rule;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import static org.apache.poi.ss.util.CellReference.convertNumToColString;

/**
 * Class: RuleTypeBuilder
 */
public class RuleTypeContainerBuilder extends RuleContainerBuilder<RuleType> {
    private static final Logger log = LoggerFactory.getLogger(RuleTypeContainerBuilder.class);

    public RuleTypeContainerBuilder(final String name, final Table<String, String, String> values) {
        super(name, values);
    }

    @Override
    public int getRuleId(Map<String, String> columns) {
        return Integer.valueOf(StringUtils.defaultIfBlank(columns.get(COLUMN_INDEX_A_RULE_TYPE_ID), "-1"));
    }

    @Override
    public String makeRuleName(final int ruleId) {
        return String.format("%s-%03d", null == ruleType ? name : ruleType.getName(), ruleId);
    }

    @Override
    public Collection<Condition<RuleType>> getConditions(final Map<String, String> columns) {
        return null;
    }

    @Override
    public Collection<Action<RuleType>> getActions(final Map<String, String> columns) {
        return null;
    }

    @Override
    public String getDescription(Map<String, String> columns) {
        return columns.get(COLUMN_INDEX_D_DESCRIPTION);
    }

    @Override
    protected Maps.EntryTransformer<String, Map<String, String>, Rule<RuleType>> getRuleTransformer() {
        return new Maps.EntryTransformer<String, Map<String, String>, Rule<RuleType>>() {
            @Override
            public Rule<RuleType> transformEntry(@Nullable final String key, @Nullable final Map<String, String> columns) {
                if (null != columns && !columns.isEmpty()) {
                    final RuleType ruleType = new RuleType.Builder().setId(getRuleId(columns)).setName(columns.get(COLUMN_INDEX_B_RULE_TYPE_NAME))
                            .setCategory(columns.get(COLUMN_INDEX_C_CATEGORY)).setDescription(getDescription(columns)).setPriority(
                                    Integer.valueOf(columns.get(COLUMN_INDEX_E_PRIORITY))).build();
                    log.debug("Created ruleType {}", ruleType);
                }
                return null;
            }
        };
    }

    private static int index = 0;
    private static final String COLUMN_INDEX_A_RULE_TYPE_ID = convertNumToColString(index++);
    private static final String COLUMN_INDEX_B_RULE_TYPE_NAME = convertNumToColString(index++);
    private static final String COLUMN_INDEX_C_CATEGORY = convertNumToColString(index++);
    private static final String COLUMN_INDEX_D_DESCRIPTION = convertNumToColString(index++);
    private static final String COLUMN_INDEX_E_PRIORITY = convertNumToColString(index++);
}
