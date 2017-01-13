package com.briarshore.rule;

import java.io.Serializable;
import java.util.Collection;

import org.slf4j.Logger;

public interface RuleEngine<T> extends Serializable {
    RuleEngine<T> addRuleContainers(Collection<RuleContainer<T>> ruleContainers);

    boolean fireRules(T t);

    boolean fireRules(String category, T t);

    Logger getLog();

    RuleEngine<T> setLog(Logger log);

    Collection<RuleContainer<T>> getRuleContainers();

    void add(RuleType ruleType, Rule<T> r);
}
