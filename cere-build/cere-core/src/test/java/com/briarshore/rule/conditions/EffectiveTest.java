package com.briarshore.rule.conditions;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class EffectiveTest {
    private Condition<?> _default;
    private Condition<?> _defaultClock;
    private Condition<?> affirmative;
    private Condition<?> negative;
    private Condition<?> affirmativeSameDate;
    private Condition<?> negativeSameDate;
    private Condition<?> affirmativeClockSameDate;
    private Condition<?> negativeClockSameDate;
    private Condition<?> affirmativeDoesNotExpire;
    private Condition<?> negativeAlwaysEffective;
    private LocalDateTime effective;
    private LocalDateTime expires;
    private LocalDateTime midnight;
    private LocalDateTime midnightPlusOneDay;
    private Clock fixedClock;

    @Before
    public void setUp() throws Exception {
        fixedClock = Clock.fixed(LocalDateTime.of(2016, Month.DECEMBER.getValue(), 31, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), ZoneOffset.UTC.normalized());
        midnight = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        _default = new Effective();
        _defaultClock = new Effective(fixedClock);
        effective = midnight.minusDays(1L);
        expires = midnight.plusDays(1L);
        affirmative = new Effective(effective, expires);
        negative = new Effective(false, effective, expires);
        affirmativeSameDate = new Effective(true, midnight, midnight);
        negativeSameDate = new Effective(false, midnight, midnight);
        affirmativeClockSameDate = new Effective(fixedClock, midnight, midnight);
        midnightPlusOneDay = midnight.plusDays(1L);
        negativeClockSameDate = new Effective(false, fixedClock, midnight, midnightPlusOneDay);
        affirmativeDoesNotExpire = new Effective(midnight, null);
        negativeAlwaysEffective = new Effective(false, null, midnight);
    }

    @Test
    public void testNull() throws Exception {
        assertTrue("expected true", _default.test(null));
        assertTrue("expected true", _defaultClock.test(null));
        assertTrue("expected true", affirmative.test(null));
        assertFalse("expected false", negative.test(null));
        assertTrue("expected true", affirmativeSameDate.test(null));
        assertFalse("expected false", negativeSameDate.test(null));
    }

    @Test
    public void testToString() throws Exception {
        final String expectedDefault = "current date may be any value.";
        assertEquals("expected match", expectedDefault, _default.toString());
        final String expectedAffirmative = format("now must be between %s and %s (inclusive)", effective, expires);
        assertEquals("expected match", expectedAffirmative, affirmative.toString());
        final String expectedNegative = format("now must not be between %s and %s (inclusive)", effective, expires);
        assertEquals("expected match", expectedNegative, negative.toString());
        final String expectedAffirmativeSameDate = format("now must be exactly %s", midnight);
        assertEquals("expected match", expectedAffirmativeSameDate, affirmativeSameDate.toString());
        final String expectedNegativeSameDate = format("now must be any date except %s", midnight);
        assertEquals("expected match", expectedNegativeSameDate, negativeSameDate.toString());
        assertEquals("expected match", format("now must be on or after %s", midnight), affirmativeDoesNotExpire.toString());
        assertEquals("expected match", format("now must not be on or before %s", midnight), negativeAlwaysEffective.toString());
    }

    @Test
    public void testEquals() throws Exception {
        assertSame("expected same", _default, _default);
        assertEquals("expected equals", _default, _default);
        assertEquals("expected equals", affirmative, affirmative);
        assertNotEquals("expected not equals", _default, affirmative);
        assertNotEquals("expected not equals", _default, negative);
        assertNotEquals("expected not equals", _default, negativeSameDate);
        assertNotEquals("expected not equals", negativeSameDate, negativeClockSameDate);
    }

    @Test
    public void pojoTests() throws Exception {
        final Effective effective = (Effective)_default;
        assertNotNull("expected non-null", effective.getClock());
        effective.setClock(null);
        assertNull("expected null", effective.getClock());
        effective.setEffectiveDate(null);
        assertNull("expected null", effective.getEffectiveDate());
        effective.setExpirationDate(null);
        assertNull("expected null", effective.getExpirationDate());
    }

    @Test
    public void tCodes() throws Exception {
        assertNotEquals("expected match", 0, _default.hashCode());
        assertNotEquals("expected match", 0, affirmative.hashCode());
        assertNotEquals("expected match", 0, affirmativeDoesNotExpire.hashCode());
        assertNotEquals("expected match", 0, negative.hashCode());
        assertNotEquals("expected match", 0, negativeAlwaysEffective.hashCode());
    }
}
