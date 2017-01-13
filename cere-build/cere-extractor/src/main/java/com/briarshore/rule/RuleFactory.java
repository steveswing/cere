package com.briarshore.rule;

import java.util.Collection;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;
import com.briarshore.util.Strings;

/**
 * Class: RuleFactory
 */
public class RuleFactory {
    public static <T> Rule<T> getRule(final Integer ruleId, final RuleType ruleType, final String ruleName, final String description, final Collection<Condition<T>> conditions,
            final Collection<Action<T>> actions) {
        final RuleImpl<T> result = new RuleImpl<T>(ruleId, ruleType, ruleName);
        if (!Strings.isBlank(description)) {
            result.setDescription(description);
        }
        result.addConditions(conditions);
        result.addActions(actions);
        return result;
    }
}
