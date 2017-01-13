package com.briarshore.rule.conditions;

import java.util.Objects;

public class BaseCondition<T> implements Condition<T> {
    public static final long serialVersionUID = 1L;
    protected boolean affirmative;

    public BaseCondition() {
        this(true);
    }

    public BaseCondition(final boolean affirmative) {
        this.affirmative = affirmative;
    }

    @Override
    public boolean test(final T t) {
        return !affirmative;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BaseCondition<?> that = (BaseCondition<?>)o;
        return affirmative == that.affirmative;
    }

    @Override
    public int hashCode() {
        return Objects.hash(affirmative);
    }
}
