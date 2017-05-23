package com.fiction.db;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class GreenDaoDbUtils {
    public static SqlBeanDao getFictionNameDb() {
        return GreenDaoUtils.getInstance().getSqlBeanDao();
    }

    public static List<SqlBean> getFictionNameAll() {
        return getFictionNameDb().loadAll();
    }

    public static boolean isEmpty(String key) {
        return getFictionNameDb().queryBuilder().where(SqlBeanDao.Properties.Title.eq(key)).unique() == null;
    }

    public static void clear(Long key) {
        getFictionNameDb().deleteByKey(key);
    }

    public static void clear() {
        getFictionNameDb().deleteAll();
    }
}
