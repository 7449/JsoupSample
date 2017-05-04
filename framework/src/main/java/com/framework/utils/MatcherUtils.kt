package com.framework.utils

import java.util.regex.Pattern

/**
 * by y on 2017/4/6.
 */

object MatcherUtils {

    /**
     * 获取String里面的所有数字
     */
    fun getInt(s: String): Int {
        return Integer.valueOf(Pattern.compile("[^0-9]").matcher(s).replaceAll("").trim { it <= ' ' })!!
    }

    /**
     * 获取妹子图搜索结果的总页数
     */
    fun getIntHasSpace(s: String): Int? {
        val replace = s.replace("下一页»", "").replace("«上一页", "")
        val split = Pattern.compile("[^0-9]").matcher(replace).replaceAll("/").split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split.isNotEmpty()) {
            return Integer.valueOf(split[split.size - 1])
        } else {
            return Integer.valueOf(split[0])
        }
    }

}
