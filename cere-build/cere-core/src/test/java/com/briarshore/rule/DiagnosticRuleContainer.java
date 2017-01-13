package com.briarshore.rule;

/**
 * Class: DiagnosticRuleContainer
 */
public class DiagnosticRuleContainer<T> extends RuleContainer<T> {
    public DiagnosticRuleContainer(final RuleType ruleType, final String name) {
        super(ruleType, name);
    }

    public boolean diagnose(final DiagnosticsResult<T> o) {
        for (final Rule<T> r : getRules()) {
            if (r instanceof DiagnosticRuleImpl) {
//                ((DiagnosticRuleImpl<T>)r).diagnose(o);
            }
        }

        return fire(o.getSubject());
    }
}
