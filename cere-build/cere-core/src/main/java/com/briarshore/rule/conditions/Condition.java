package com.briarshore.rule.conditions;

import java.io.Serializable;

@FunctionalInterface
public interface Condition<T> extends Serializable {
    boolean test(T t);
}
