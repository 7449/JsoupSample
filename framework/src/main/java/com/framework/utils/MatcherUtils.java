package com.framework.utils;

import java.util.regex.Pattern;

/**
 * by y on 2017/4/6.
 */

public class MatcherUtils {

    private MatcherUtils() {
    }

    /**
     * 获取String里面的所有数字
     */
    public static int getInt(String s) {
        return Integer.valueOf(Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim());
    }
}
