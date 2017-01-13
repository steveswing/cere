package com.briarshore.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class IfElseDemoPredicates {
    private int input;
    private char expected;
    private Predicate<Integer> scored90 = score -> score >= 90;
    private Predicate<Integer> scored80 = score -> score >= 80;
    private Predicate<Integer> scored70 = score -> score >= 70;
    private Predicate<Integer> scored60 = score -> score >= 60;
    private Predicate<Integer> scored0 = score -> score >= 0;
    private Predicate<Integer> scoredA = scored90;
    private Predicate<Integer> scoredB = scored80.and(scored90.negate());
    private Predicate<Integer> scoredC = scored70.and(scored80.negate());
    private Predicate<Integer> scoredD = scored60.and(scored70.negate());
    private Predicate<Integer> scoredF = scored0.and(scored60.negate());
    private List<Function<Integer, Character>> rules = initialize();

    public IfElseDemoPredicates(final int input, final char expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{100, 'A'}, {91, 'A'}, {90, 'A'}, {89, 'B'}, {81, 'B'}, {80, 'B'},
                {79, 'C'}, {71, 'C'}, {70, 'C'}, {69, 'D'}, {61, 'D'}, {60, 'D'}, {59, 'F'}, {0, 'F'}});
    }

    private List<Function<Integer, Character>> initialize() {
        final List<Function<Integer, Character>> result = new ArrayList<>();
        result.add((score) -> scoredA.test(score) ? 'A' : null);
        result.add((score) -> scoredB.test(score) ? 'B' : null);
        result.add((score) -> scoredC.test(score) ? 'C' : null);
        result.add((score) -> scoredD.test(score) ? 'D' : null);
        result.add((score) -> scoredF.test(score) ? 'F' : null);
        return result;
    }

    @Test
    public void testScores() {
        char grade;

        if (scoredA.test(input)) {
            grade = 'A';
        } else if (scoredB.test(input)) {
            grade = 'B';
        } else if (scoredC.test(input)) {
            grade = 'C';
        } else if (scoredD.test(input)) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        assertEquals(String.format("expected match for %d", input), expected, grade);
    }

    @Test
    public void alternativeScoring() throws Exception {
        final Optional<Character> first = rules.stream().map(f -> f.apply(input)).filter(Objects::nonNull).findFirst();
        assertTrue("expected true", first.isPresent());
        final char grade = first.get();
        assertEquals("expected match", expected, grade);
    }
}



