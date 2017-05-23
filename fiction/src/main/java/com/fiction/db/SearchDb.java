package com.fiction.db;

/**
 * by y on 2017/1/8.
 */

public class SearchDb {
    public static void insert(String suffix) {
        if (GreenDaoDbUtils.isEmpty(suffix)) {
            GreenDaoDbUtils.getFictionNameDb().insert(new SqlBean(null, suffix));
        }
    }
}
