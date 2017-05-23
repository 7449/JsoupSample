package com.framework.utils;

/**
 * by y on 2017/4/28
 */

public class StringUtils {


    public static String getString(String s, String mark) {
        char[] chars = s.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                buffer.append(chars[i]).append(mark);
            } else {
                buffer.append(chars[i]);
            }
        }

        return buffer.toString();
    }


}
