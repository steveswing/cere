package com.briarshore.rule;

import java.io.File;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Table;
import com.google.common.io.Files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Class: RuleExtractorTest
 */
public class RuleExtractorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testExtractRules() throws Exception {
        final String ruleSourcePath = "/src/test/resources/rules.xlsx";
        final File ruleSource = new File(new File("."), Files.simplifyPath(ruleSourcePath));
//        System.out.println("ruleSource.getCanonicalPath() = " + ruleSource.getCanonicalPath());
        assertTrue(String.format("Expected rule source %s to exist", ruleSourcePath), ruleSource.exists());
        final Map<String, Table<String, String, String>> rules = RuleExtractor.extractRules(ruleSource);
        assertNotNull("expected a non-null collection", rules);
        assertFalse("expected a non-empty collection", rules.isEmpty());
    }
}
