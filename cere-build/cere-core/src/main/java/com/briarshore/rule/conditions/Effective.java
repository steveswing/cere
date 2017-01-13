package com.briarshore.rule.conditions;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Stream;

import static java.lang.String.format;

public class Effective<T> extends BaseCondition<T> {
    private static final long serialVersionUID = 1L;
    private Clock clock = Clock.systemDefaultZone();
    private LocalDateTime effectiveDate;
    private LocalDateTime expirationDate;

    public Effective() {
        super(true);
    }

    public Effective(final Clock clock) {
        super(true);
        setClock(clock);
    }

    public Effective(final Clock clock, final LocalDateTime effectiveDate, final LocalDateTime expirationDate) {
        super(true);
        setClock(clock);
        setEffectiveDate(effectiveDate);
        setExpirationDate(expirationDate);
    }

    public Effective(final LocalDateTime effectiveDate, final LocalDateTime expirationDate) {
        super(true);
        setEffectiveDate(effectiveDate);
        setExpirationDate(expirationDate);
    }

    public Effective(final boolean affirmative, final LocalDateTime effectiveDate, final LocalDateTime expirationDate) {
        super(affirmative);
        setEffectiveDate(effectiveDate);
        setExpirationDate(expirationDate);
    }

    public Effective(final boolean affirmative, final Clock clock, final LocalDateTime effectiveDate, final LocalDateTime expirationDate) {
        super(affirmative);
        setClock(clock);
        setEffectiveDate(effectiveDate);
        setExpirationDate(expirationDate);
    }

    public boolean equals(final Object o) {
        if (!super.equals(o)) {
            return false;
        }

        final Effective that = (Effective)o;

        if (effectiveDate != null ? !effectiveDate.equals(that.effectiveDate) : that.effectiveDate != null) {
            return false;
        }
        if (expirationDate != null ? !expirationDate.equals(that.expirationDate) : that.expirationDate != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (effectiveDate != null ? effectiveDate.hashCode() : 0);
        result = 29 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        return result;
    }

    public boolean test(final T o) {
        final LocalDateTime now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.DAYS);
        return null == effectiveDate && null == expirationDate ? affirmative : (null != effectiveDate && !effectiveDate.isAfter(now) && null != expirationDate && !expirationDate.isBefore(now)) &&
                affirmative;
    }

    public String toString() {
        final String result = "now must ";
        if (effectiveDate != null && expirationDate != null) {
            if (effectiveDate.equals(expirationDate)) {
                return format("%sbe %s%s", result, affirmative ? "exactly " : "any date except ", effectiveDate);
            } else {
                return format("%s%sbe between %s and %s (inclusive)", result, affirmative ? "" : "not ", effectiveDate, expirationDate);
            }
        } else {
            if (effectiveDate == null && expirationDate == null) {
                return "current date may be any value.";
            } else {
                return format("%s%sbe on or %s%s", result, affirmative ? "" : "not ", effectiveDate == null ? "before " : "after ", effectiveDate == null ? expirationDate : effectiveDate);
            }
        }
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(final Clock clock) {
        this.clock = clock;
    }

    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(final LocalDateTime value) {
        effectiveDate = value;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final LocalDateTime value) {
        expirationDate = value;
    }
}
