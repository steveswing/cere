package com.briarshore.rule;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.Nullable;

import com.briarshore.util.Strings;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import static com.briarshore.util.Strings.nullSafeCaseInsensitiveComparator;

public class RuleType implements Comparable<RuleType>, Serializable {
    static transient final Comparator<RuleType> ruleTypePriorityComparator = new Comparator<RuleType>() {
        @Override
        public int compare(final RuleType rt1, final RuleType rt2) {
            return rt1 == rt2 ? 0 : null != rt1 && null != rt2 ? rt1.getPriority() == rt2.getPriority() ? safeCompare(rt1.getName(), rt2.getName()) : rt1.getPriority() < rt2.getPriority() ? -1 : 1
                    : null != rt1 ? -1 : 1;
        }

        private int safeCompare(final Comparable dis, final Comparable dat) {
            return dis == dat ? 0 : dis != null && dat != null ? dis.compareTo(dat) : dis == null ? -1 : 1;
        }
    };
    private static final long serialVersionUID = 0L;
    private static final SortedMap<String, RuleType> ruleTypesByName = new TreeMap<>(nullSafeCaseInsensitiveComparator);
    private static final SortedMap<String, SortedSet<RuleType>> ruleTypesByCategory = new TreeMap<>(nullSafeCaseInsensitiveComparator);
    private static final SortedSet<RuleType> prioritizedRuleTypes = new TreeSet<>(ruleTypePriorityComparator);

    @XStreamAsAttribute
    private final long id;
    @XStreamAsAttribute
    private final String name;
    @XStreamAsAttribute
    private final String category;
    @XStreamAsAttribute
    private final String description;
    @XStreamAsAttribute
    private final int priority;

    private RuleType(final long id, final String name, final String category, final String description, final int priority) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.priority = priority;
        ruleTypesByName.put(name, this);
        ruleTypesByCategory.computeIfAbsent(category, s -> new TreeSet<>(ruleTypePriorityComparator)).add(this);
        prioritizedRuleTypes.add(this);
    }

    public static SortedSet<RuleType> getPrioritizedRuleTypes() {
        return Collections.unmodifiableSortedSet(prioritizedRuleTypes);
    }

    public static RuleType findByName(final String name) {
        return ruleTypesByName.get(name);
    }

    public static SortedSet<RuleType> findByCategory(final String category) {
        return ruleTypesByCategory.get(category);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RuleType that = (RuleType)o;
        return id == that.id && priority == that.priority && Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, description, priority);
    }

    @Override
    public int compareTo(@Nullable final RuleType that) {
        return ruleTypePriorityComparator.compare(this, that);
    }

    public static class Builder {
        private long id;
        private String name;
        private String category;
        private String description;
        private int priority;

        public Builder() {
        }

        public long getId() {
            return id;
        }

        public Builder setId(final long id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public String getCategory() {
            return category;
        }

        public Builder setCategory(final String category) {
            this.category = category;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Builder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public int getPriority() {
            return priority;
        }

        public Builder setPriority(final int priority) {
            this.priority = priority;
            return this;
        }

        public RuleType build() {
            if (Strings.isBlank(name)) {
                throw new IllegalStateException("name must be non-null, id must be > -1");
            }

            return new RuleType(id, name, category, description, priority);
        }
    }
}
