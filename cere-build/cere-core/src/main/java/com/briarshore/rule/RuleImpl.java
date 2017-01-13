package com.briarshore.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import com.briarshore.rule.actions.Action;
import com.briarshore.rule.conditions.Condition;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("rule")
public class RuleImpl<T> implements Rule<T> {
    private static final long serialVersionUID = 0L;
    private RuleType ruleType;
    @XStreamAsAttribute
    private int id;
    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String description;
    private Collection<Condition<T>> conditions = new ArrayList<>();
    private Collection<Action<T>> actions = new ArrayList<>();

    public RuleImpl() {
    }

    public RuleImpl(final int id, final RuleType ruleType, final String name) {
        this.id = id;
        this.ruleType = ruleType;
        this.name = name;
    }

    public RuleImpl(final int id, final RuleType ruleType, final Collection<Condition<T>> conditions, final Collection<Action<T>> actions) {
        this.id = id;
        this.ruleType = ruleType;
        addConditions(conditions);
        addActions(actions);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    @Override
    public RuleType getRuleType() {
        return ruleType;
    }

    public Collection<Condition<T>> getConditions() {
        return Collections.unmodifiableCollection(conditions);
    }

    public Collection<Action<T>> getActions() {
        return Collections.unmodifiableCollection(actions);
    }

    @Override
    public Rule<T> add(final Condition<T> c) {
        if (null != c && !conditions.contains(c)) {
            conditions.add(c);
        }
        return this;
    }

    @Override
    public Rule<T> add(final Action<T> a) {
        if (null != a && !actions.contains(a)) {
            actions.add(a);
        }
        return this;
    }

    @Override
    public boolean fire(final T t) {
        if (conditions.stream().allMatch(c -> c.test(t))) {
            actions.forEach(a -> a.perform(this, t));
            return true;
        }
        return false;
    }

    public void addConditions(final Collection<Condition<T>> conditions) {
        if (null != conditions) {
            this.conditions.addAll(conditions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
    }

    public void addActions(final Collection<Action<T>> actions) {
        if (null != actions) {
            this.actions.addAll(actions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
    }

    public boolean remove(final Condition<T> c) {
        return null != c && conditions.contains(c) && conditions.remove(c);
    }

    public boolean remove(final Action<T> a) {
        return null != a && actions.contains(a) && actions.remove(a);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RuleImpl<?> rule = (RuleImpl<?>)o;
        return Objects.equals(id, rule.id) && Objects.equals(conditions, rule.conditions) && Objects.equals(actions, rule.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, conditions, actions);
    }

    @Override
    public String toString() {
        return Objects.toString(id, "");
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int compareTo(final Rule<T> that) {
        int result = this == that ? 0 : null != that ? safeCompare(getRuleType(), that.getRuleType()) : 1;
        if (0 == result) {
            result = safeCompare(getId(), that.getId());
            if (0 == result) {
                result = safeCompare(getName(), that.getName());
            }
        }
        return result;
    }

    private int safeCompare(Comparable dis, Comparable dat) {
        return dis == dat ? 0 : dis != null && dat != null ? dis.compareTo(dat) : dis == null ? -1 : 1;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
