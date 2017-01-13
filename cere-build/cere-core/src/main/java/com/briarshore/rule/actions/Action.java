package com.briarshore.rule.actions;

import java.io.Serializable;

import com.briarshore.rule.Rule;

@FunctionalInterface
public interface Action<T> extends Serializable {
    void perform(Rule<T> rule, T t);
}
