package com.briarshore.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import javax.annotation.Nullable;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class RuleContainer<T> implements Comparable<RuleContainer<T>>, Serializable {
    public static final transient Comparator<RuleContainer> ruleContainerComparator =
            (Comparator<RuleContainer>)(dis, dat) -> dis == dat ? 0 : null != dis && null != dat ? RuleType.ruleTypePriorityComparator.compare(dis.getRuleType(), dat.getRuleType())
                    : null == dis ? -1 : 1;
    private static final long serialVersionUID = 0L;
    private RuleType ruleType;
    @XStreamAsAttribute
    private String name;
    private Collection<Rule<T>> rules = new ArrayList<>();

    public RuleContainer() {
    }

    public RuleContainer(final RuleType ruleType, final String name) {
        this.ruleType = ruleType;
        this.name = name;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(final RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public RuleContainer<T> add(final Rule<T> rule) {
        if (null != rule && null != rule.getConditions() && null != rule.getActions() && !rule.getConditions().isEmpty() && !rule.getActions().isEmpty()) {
            rules.add(rule);
        }
        return this;
    }

    public int getRuleCount() {
        return null == ruleType && "ruleTypes".equalsIgnoreCase(name) ? RuleType.getPrioritizedRuleTypes().size() : rules.size();
    }

    public Collection<Rule<T>> getRules() {
        return Collections.unmodifiableCollection(rules);
    }

    public void setRules(final Collection<Rule<T>> rules) {
        this.rules.clear();
        if (null != rules && !rules.isEmpty()) {
            addAll(rules);
        }
    }

    public void addAll(final Collection<Rule<T>> rules) {
        if (null != rules && !rules.isEmpty()) {
            rules.stream().filter(Objects::nonNull).forEach(r -> add(r));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RuleContainer<?> that = (RuleContainer<?>)o;

        return Objects.equals(ruleType, that.ruleType) && Objects.equals(ruleContainerComparator, that.ruleContainerComparator) && Objects.equals(name, that.name) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleType, ruleContainerComparator, name, rules);
    }

    public boolean fire(final T t) {
        return rules.stream().anyMatch(r -> r.fire(t));
    }

    @Override
    public int compareTo(@Nullable final RuleContainer<T> that) {
        return this == that ? 0 : null == that ? 1 : RuleType.ruleTypePriorityComparator.compare(this.getRuleType(), that.getRuleType());
    }

    @Override
    public String toString() {
        return name;
    }
}
