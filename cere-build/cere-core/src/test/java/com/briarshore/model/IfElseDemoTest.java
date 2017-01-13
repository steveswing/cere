package com.briarshore.model;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IfElseDemoTest {
    private int input;
    private char expected;

    public IfElseDemoTest(int input, char expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100, 'A'}, {91, 'A'}, {90, 'A'}, {89, 'B'}, {81, 'B'}, {80, 'B'}, {79, 'C'},
                {71, 'C'}, {70, 'C'}, {69, 'D'}, {61, 'D'}, {60, 'D'}, {59, 'F'}, {0, 'F'}});
    }

    @Test
    public void testScores() {
        int testscore = input;
        char grade;
        if (testscore >= 90) {
            grade = 'A';
        } else if (testscore >= 80) {
            grade = 'B';
        } else if (testscore >= 70) {
            grade = 'C';
        } else if (testscore >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        assertEquals(String.format("expected match for %d", input), expected, grade);
    }
}



