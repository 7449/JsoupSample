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

    /**
     * 获取妹子图搜索结果的总页数
     */
    public static Integer getIntHasSpace(String s) {
        String replace = s.replace("下一页»", "").replace("«上一页", "");
        String[] split = Pattern.compile("[^0-9]").matcher(replace).replaceAll("/").split("/");
        if (split.length >= 1) {
            return Integer.valueOf(split[split.length - 1]);
        } else {
            return Integer.valueOf(split[0]);
        }
    }

}
