package com.briarshore.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import static com.briarshore.util.Strings.nullSafeCaseInsensitiveComparator;

@XStreamAlias("rules")
public class DefaultRuleEngine<T> implements RuleEngine<T> {
    private static final long serialVersionUID = 0L;
    @XStreamAlias("rule-types")
    protected SortedSet<RuleType> ruleTypes = new TreeSet<>(RuleType.ruleTypePriorityComparator);
    @XStreamAlias("rule-containers")
    protected SortedMap<RuleType, RuleContainer<T>> ruleContainersByRuleType = new TreeMap<>(RuleType.ruleTypePriorityComparator);
    @XStreamOmitField
    protected SortedMap<String, SortedSet<RuleContainer<T>>> ruleContainersByCategory = new TreeMap<>(nullSafeCaseInsensitiveComparator);
    private transient Logger log = LoggerFactory.getLogger(DefaultRuleEngine.class);

    public DefaultRuleEngine() {
    }

    public synchronized void add(final RuleType type, final Rule<T> rule) {
        if (null == type || null == rule || hasNoConditions(rule) || hasNoActions(rule)) {
            return;
        }

        getRuleContainer(type).add(rule);
    }

    private boolean hasNoConditions(final Rule rule) {
        return isNullOrEmptyList(rule.getConditions());
    }

    private boolean hasNoActions(final Rule rule) {
        return isNullOrEmptyList(rule.getActions());
    }

    private boolean isNullOrEmptyList(final Collection c) {
        return null == c || c.isEmpty();
    }

    public RuleContainer<T> findRuleContainer(final RuleType type) {
        return ruleContainersByRuleType.computeIfAbsent(type, rt -> new RuleContainer<T>(rt, rt.getName()));
    }

    protected RuleContainer<T> getRuleContainer(RuleType type) {
        return findRuleContainer(type);
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public RuleEngine<T> setLog(final Logger log) {
        this.log = log;
        return this;
    }

    public SortedSet<RuleType> getRuleTypes() {
        return Collections.unmodifiableSortedSet(ruleTypes);
    }

    public void setRuleTypes(@Nullable final Set<RuleType> ruleTypes) {
        this.ruleTypes.clear();
        if (null != ruleTypes && !ruleTypes.isEmpty()) {
            for (final RuleType r : ruleTypes) {
                // Kind of a hacky way around XStream not having a way to use the correct constructor with all the attributes.
                this.ruleTypes.add(new RuleType.Builder().setId(r.getId()).setName(r.getName()).setCategory(r.getCategory()).setDescription(r.getDescription()).setPriority(r.getPriority()).build());
            }
        }
    }

    @Override
    public Collection<RuleContainer<T>> getRuleContainers() {
        return ruleContainersByRuleType.values();
    }

    public void setRuleContainersByRuleType(@Nullable final SortedSet<RuleContainer<T>> ruleContainers) {
        this.ruleContainersByRuleType.clear();
        if (null != ruleContainers) {
            addRuleContainers(ruleContainers);
        }
    }

    @Override
    public RuleEngine<T> addRuleContainers(@Nullable final Collection<RuleContainer<T>> ruleContainers) {
        if (null != ruleContainers) {
            ruleContainers.stream().filter(c -> null != c.getRuleType()).forEach((RuleContainer<T> c) -> {
                ruleTypes.add(c.getRuleType());
                this.ruleContainersByRuleType.put(c.getRuleType(), c);
                this.ruleContainersByCategory.computeIfAbsent(c.getRuleType().getCategory(), s -> new TreeSet<>(RuleContainer.ruleContainerComparator)).add(c);
            });
        }
        return this;
    }

    @Override
    public boolean fireRules(final String category, final T t) {
        return !ruleContainersByCategory.getOrDefault(category, Collections.emptySortedSet()).stream().filter(c -> c.fire(t)).collect(Collectors.toList()).isEmpty();
    }

    @Override
    public boolean fireRules(final T t) {
        return !ruleContainersByRuleType.values().stream().filter(c -> c.fire(t)).collect(Collectors.toList()).isEmpty();
    }
}
