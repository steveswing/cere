package com.briarshore.predicates;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;

import com.briarshore.model.Customer;
import com.briarshore.model.CustomerType;

import static java.lang.Integer.MAX_VALUE;
import static java.time.Clock.offset;
import static java.time.Clock.systemDefaultZone;
import static java.time.Duration.of;
import static java.time.ZoneId.systemDefault;
import static java.time.ZoneOffset.of;
import static java.time.temporal.ChronoUnit.MONTHS;

public class Predicates {
    private static final Map<CustomerType, Integer> salesThresholds = initializeSalesThresholds();
    private static final Instant sixMonthsAgo = offset(systemDefaultZone(), of(-6L, MONTHS)).instant();
    public Predicate<Customer> small = c -> CustomerType.SMALL.equals(c.getType());
    public Predicate<Customer> medium = c -> CustomerType.MEDIUM.equals(c.getType());
    public Predicate<Customer> large = c -> CustomerType.LARGE.equals(c.getType());
    public Predicate<Customer> smallOrLarge = small.or(large);
    public Predicate<Customer> notMedium = medium.negate();
    public Predicate<Customer> vip = c -> c.getTotalSales() > salesThresholds.getOrDefault(c.getType(), MAX_VALUE);
    public Predicate<Customer> mediumVip = medium.and(vip);
    public Predicate<Customer> recentSales = c -> sixMonthsAgo.isBefore(asInstant(c.getRecentSalesDate()));
    public Predicate<Customer> currentMedVip = mediumVip.and(recentSales);

    public Function<Customer, Customer> applyDiscount = c -> c;
    public Function<Customer, Customer> freeShipping = c -> c;

    private static Map<CustomerType, Integer> initializeSalesThresholds() {
        final Map<CustomerType, Integer> result = new TreeMap<>();
        result.put(CustomerType.SMALL, 1000);
        result.put(CustomerType.MEDIUM, 5000);
        result.put(CustomerType.LARGE, 10000);
        return result;
    }

    private Map<Predicate<Customer>, Function<Customer, Customer>> initializeActions() {
        final Map<Predicate<Customer>, Function<Customer, Customer>> result = new TreeMap<>();
        result.put(currentMedVip, applyDiscount);
        result.put(recentSales, freeShipping);
        return result;
    }

    private void applyActions() {
        final Map<Predicate<Customer>, Function<Customer, Customer>> actions = initializeActions();
        final List<Customer> customers = getCustomers();
        for (final Customer customer : customers) {
            actions.entrySet().stream().filter(e -> e.getKey().test(customer)).forEach(e -> e.getValue().apply(customer));
        }
    }

    private List<Customer> getCustomers() {
        return new ArrayList<>();
    }

    private Instant asInstant(final LocalDateTime d) {
        return d.toInstant(of(systemDefault().getId()));
    }
}
