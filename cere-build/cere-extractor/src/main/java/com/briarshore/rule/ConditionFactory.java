package com.briarshore.rule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import com.briarshore.rule.conditions.Condition;
import com.briarshore.rule.conditions.Effective;
import com.briarshore.util.Strings;
import com.briarshore.utility.Constants;

/**
 * Class: ConditionFactory
 */
public class ConditionFactory<T> {
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private ConditionFactory() {
    }

    public static Boolean asAffirmative(final String s) {
        return BooleanUtils.toBooleanObject(s);
    }

    public static Set<String> asSet(final String s) {
        return Strings.isBlank(s) ? null : Strings.newCaseInsensitiveSet(StringUtils.stripAll(StringUtils.split(s, ",")));
    }


    public static <T> Condition<T> defined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> containsCompletedSystem(final Set<String> systems, final Boolean affirmative) {
        return null == affirmative || null == systems || systems.isEmpty() ? null : null;
    }

    public static <T> Condition<T> reasonCode(final Boolean affirmative, final Set<String> reasonCodes) {
        return null == affirmative || null == reasonCodes || reasonCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<MutablePair<T, T>> leftReasonCode(final Boolean affirmative, final Set<String> reasonCodes) {
        return null == affirmative || null == reasonCodes || reasonCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<MutablePair<T, T>> rightReasonCode(final Boolean affirmative, final Set<String> reasonCodes) {
        return null == affirmative || null == reasonCodes || reasonCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<T> skip(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> functionCode(final Boolean affirmative, final Set<String> functionCodes) {
        return null == affirmative || null == functionCodes || functionCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<MutablePair<T, T>> leftFunctionCode(final Boolean affirmative, final Set<String> functionCodes) {
        return null == affirmative || null == functionCodes || functionCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<MutablePair<T, T>> rightFunctionCode(final Boolean affirmative, final Set<String> functionCodes) {
        return null == affirmative || null == functionCodes || functionCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<T> scheduledDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> receivedDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> scheduledAfterReceived(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> receivedOnOrBeforeOperationalDayStart(final Boolean affirmative, final LocalTime amsOperationalDayStart) {
        return null == affirmative || null == amsOperationalDayStart ? null : null;
    }

    public static <T> Condition<T> type(final Boolean affirmative, final Set<String> types) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> dnpScheduledBeforeHoliday(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> expectedDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> receivedDateBeforeExpectedDate(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> receivedDateBeforeEffectiveDate(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> startDateAfterCutoff(final Boolean affirmative, final LocalTime cutoffTime) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> effective(final Boolean affirmative, final LocalDateTime effective, final LocalDateTime expires) {
        return null == affirmative || null == effective ? null : new Effective<T>(affirmative, effective, expires);
    }

    public static LocalDateTime asDateTime(final String s) {
        return Strings.isBlank(s) ? null : dateTimeFormatter.parse(s, temporal -> LocalDateTime.from(temporal));
    }

    public static <T> Condition<T> expired(final Boolean affirmative, final LocalDateTime expired) {
        return null == affirmative || null == expired ? null : null;
    }

    public static <T> Condition<T> completed(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> orderStatus(final Boolean affirmative, final Set<String> statusCodes) {
        return null == affirmative || null == statusCodes || statusCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<T> typeFunctionPriority(final Boolean affirmative, final Set<String> values) {
        return null == affirmative || null == values || values.isEmpty() ? null : null;
    }

    public static <T> Condition<T> slaExpectedDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> remoteStatusCode(final Boolean affirmative, final Set<String> remoteStatusCodes) {
        return null == affirmative || null == remoteStatusCodes || remoteStatusCodes.isEmpty() ? null : null;
    }

    public static <T> Condition<T> remoteStatusCodeDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> remoteStatusCodeBlank(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static ZoneId asTimeZone(final String timeZone) {
        return Strings.isBlank(timeZone) || !ZoneId.getAvailableZoneIds().contains(timeZone) ? null : ZoneId.of(timeZone);
    }

    public static LocalTime asLocalTimeWithZone(final String time, final ZoneId timeZone) {
        return Strings.isBlank(time) || null == timeZone ? null : LocalTime.parse(time).atDate(LocalDate.now()).atZone(Constants.easternTimeZone).toLocalTime();
    }

    public static Set<DayOfWeek> asDaysOfWeek(final String s) {
        return Strings.isBlank(s) ? null : new TreeSet<>(Stream.of(StringUtils.split(s, ", ")).map(DayOfWeek::valueOf).collect(Collectors.toSet()));
    }

    public static <T> Condition<T> receivedDateDayOfWeek(final Boolean affirmative, final Set<DayOfWeek> daysOfWeek) {
        return null == affirmative || null == daysOfWeek || daysOfWeek.isEmpty() ? null : null;
    }

    public static <T> Condition<T> receivedDateOnOrBeforeAmsOperationalDayStart2015(final Boolean affirmative, final LocalTime amsOperationalDayStart) {
        return null == affirmative || null == amsOperationalDayStart ? null : null;
    }

    public static <T> Condition<T> receivedDateAfterCutoff(final Boolean affirmative, final LocalTime cutoffTime) {
        return null == affirmative || null == cutoffTime ? null : null;
    }

    public static <T> Condition<T> amsStartDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> endDateDefined(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> scheduledDateAfterCutoff(final Boolean affirmative, final LocalTime cutoffTime) {
        return null == affirmative || cutoffTime == null ? null : null;
    }

    public static <T> Condition<T> endedOnScheduledDate(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }

    public static <T> Condition<T> endDateAfterCutoffTime(final Boolean affirmative, final LocalTime cutoffTime) {
        return null == affirmative || null == cutoffTime ? null : null;
    }

    public static <T> Condition<T> endedBeforeScheduledDate(final Boolean affirmative) {
        return null == affirmative ? null : null;
    }
}
