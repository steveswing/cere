package com.briarshore.rule;

import java.io.Serializable;
import java.util.Collection;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;

public interface Rule<T> extends Comparable<Rule<T>>, Serializable {
    RuleType getRuleType();

    int getId();

    String getName();

    Collection<Condition<T>> getConditions();

    Collection<Action<T>> getActions();

    Rule<T> add(Condition<T> c);

    Rule<T> add(Action<T> a);

    boolean fire(T t);
}




