package com.briarshore.rule.actions;

import com.briarshore.rule.Rule;

public class BaseAction<T> implements Action<T> {
    public static final long serialVersionUID = 1L;

    @Override
    public void perform(Rule<T> rule, T t) {
    }
}
