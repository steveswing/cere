package com.briarshore.rule;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Class: ConditionFactoryTest
 */
public class ConditionFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAsAffirmativeNulls() throws Exception {
        Assert.assertNull("expected null", ConditionFactory.asAffirmative(null));
        Assert.assertNull("expected null", ConditionFactory.asAffirmative(StringUtils.EMPTY));
        Assert.assertNull("expected null", ConditionFactory.asAffirmative(" "));
        Assert.assertNull("expected null", ConditionFactory.asAffirmative("other"));
    }

    @Test
    public void testAsAffirmativeLower() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("false");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("true");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsAffirmativeUpper() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("FALSE");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("TRUE");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsAffirmativeCapitalized() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("False");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("True");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsAffirmativeYesNoLower() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("no");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("yes");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsAffirmativeYesNoUpper() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("NO");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("YES");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsAffirmativeYesNoCapitalized() throws Exception {
        final Boolean affirmativeFalse = ConditionFactory.asAffirmative("No");
        Assert.assertNotNull("expected non-null", affirmativeFalse);
        Assert.assertFalse("expected false", affirmativeFalse);
        final Boolean affirmativeTrue = ConditionFactory.asAffirmative("Yes");
        Assert.assertNotNull("expected non-null", affirmativeTrue);
        Assert.assertTrue("expected true", affirmativeTrue);
    }

    @Test
    public void testAsSet() throws Exception {
        Assert.assertNull("expected null from null", ConditionFactory.asSet(null));
        Assert.assertNull("expected null from StringUtils.EMPTY", ConditionFactory.asSet(StringUtils.EMPTY));
        final Set<String> empty = ConditionFactory.asSet(",");
        Assert.assertNotNull("expected non-null from \",\"", empty);
        Assert.assertTrue("expected empty set", empty.isEmpty());
        final Set<String> one = ConditionFactory.asSet("one");
        Assert.assertNotNull("expected non-null from \"one\"", one);
        Assert.assertFalse("expected non-empty from \"one\"", one.isEmpty());
        final Set<String> two = ConditionFactory.asSet("one,two");
        Assert.assertNotNull("expected non-null with \"one,two\"", two);
        Assert.assertFalse("expected non-empty with \"one,two\"", two.isEmpty());
        Assert.assertEquals("expected match", 2, two.size());
        final Set<String> spacedThree = ConditionFactory.asSet("   one   ,  two   , three  ");
        Assert.assertNotNull("expected non-null with \"   one   ,  two   , three  \"", spacedThree);
        Assert.assertFalse("expected non-empty with \"   one   ,  two   , three  \"", spacedThree.isEmpty());
        Assert.assertEquals("expected match", 3, spacedThree.size());
        Assert.assertTrue("expected contains \"one\"", spacedThree.contains("one"));
        Assert.assertTrue("expected contains \"two\"", spacedThree.contains("two"));
        Assert.assertTrue("expected contains \"three\"", spacedThree.contains("three"));
        Assert.assertTrue("expected contains \" three \"", spacedThree.contains(" three "));
        Assert.assertFalse("expected not contains \"four\"", spacedThree.contains("four"));
    }

    @Test
    public void testAsFunctionCodes() throws Exception {

    }

    @Test
    public void testAsReasonCodes() throws Exception {

    }

    @Test
    public void testAsRemoteStatusCodes() throws Exception {

    }

    @Test
    public void testContainsCompletedSystem() throws Exception {

    }

    @Test
    public void testReasonCode() throws Exception {

    }

    @Test
    public void testLeftReasonCode() throws Exception {

    }

    @Test
    public void testRightReasonCode() throws Exception {

    }

    @Test
    public void testSkip() throws Exception {

    }

    @Test
    public void testFunctionCode() throws Exception {

    }

    @Test
    public void testLeftFunctionCode() throws Exception {

    }

    @Test
    public void testRightFunctionCode() throws Exception {

    }

    @Test
    public void testScheduledDateDefined() throws Exception {

    }

    @Test
    public void testReceivedDateDefined() throws Exception {

    }

    @Test
    public void testScheduledAfterReceived() throws Exception {

    }

    @Test
    public void testReceivedOnOrBeforeOperationalDayStart() throws Exception {

    }

    @Test
    public void testType() throws Exception {

    }

    @Test
    public void testDnpScheduledBeforeHoliday() throws Exception {

    }

    @Test
    public void testExpectedDateDefined() throws Exception {

    }

    @Test
    public void testReceivedDateBeforeExpectedDate() throws Exception {

    }

    @Test
    public void testReceivedDateBeforeEffectiveDate() throws Exception {

    }

    @Test
    public void testStartDateAfterCutoff() throws Exception {

    }

    @Test
    public void testEffective() throws Exception {

    }

    @Test
    public void testAsDateTime() throws Exception {

    }

    @Test
    public void testExpired() throws Exception {

    }

    @Test
    public void testCompleted() throws Exception {

    }

    @Test
    public void testOrderStatus() throws Exception {

    }

    @Test
    public void testTypeFunctionPriority() throws Exception {

    }

    @Test
    public void testSlaExpectedDateDefined() throws Exception {

    }

    @Test
    public void testRemoteStatusCode() throws Exception {

    }

    @Test
    public void testRemoteStatusCodeDefined() throws Exception {

    }

    @Test
    public void testRemoteStatusCodeBlank() throws Exception {

    }

    @Test
    public void testAsTimeZone() throws Exception {
        Assert.assertNull("expected null", ConditionFactory.asTimeZone(null));
        Assert.assertNull("expected null", ConditionFactory.asTimeZone(StringUtils.EMPTY));
        Assert.assertNull("expected null", ConditionFactory.asTimeZone("Invalid"));
    }

    @Test
    public void testAsDaysOfWeek() throws Exception {

    }

    @Test
    public void testReceivedDateDayOfWeek() throws Exception {

    }

    @Test
    public void testReceivedDateOnOrBeforeAmsOperationalDayStart2015() throws Exception {

    }

    @Test
    public void testReceivedDateAfterCutoff() throws Exception {

    }
}
