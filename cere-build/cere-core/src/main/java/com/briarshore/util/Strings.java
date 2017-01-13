package com.briarshore.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Strings {
    public static final String EMPTY = "";
    public static final Comparator<String> nullSafeCaseInsensitiveComparator = (s1, s2) -> trimToEmpty(s1).compareToIgnoreCase(trimToEmpty(s2));

    private static String trimToEmpty(final String s) {
        return null == s ? EMPTY : s.trim();
    }

    public static boolean isBlank(final String s) {
        return 0 == trimToEmpty(s).length();
    }

    public static Set<String> newCaseInsensitiveSet(final String[] strings) {
        final Set<String> result = new TreeSet<>(nullSafeCaseInsensitiveComparator);
        if (null != strings && 0 < strings.length) {
            Collections.addAll(result, strings);
        }
        return result;
    }
}
