package com.framework.utils

/**
 * by y on 2017/4/28
 */

object StringUtils {


    fun getString(s: String, mark: String): String {
        val chars = s.toCharArray()

        val buffer = StringBuilder()

        for (i in chars.indices) {
            if (i != chars.size - 1) {
                buffer.append(chars[i].toString()).append(mark)
            } else {
                buffer.append(chars[i].toString())
            }
        }

        return buffer.toString()
    }


}
