package com.image.collection;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class GreenDaoDbUtils {
    public static CollectionModelDao getCollectionDb() {
        return GreenDaoUtils.getInstance().getCollectionModelDao();
    }

    public static List<CollectionModel> getCollectionAll() {
        return getCollectionDb().loadAll();
    }

    public static boolean isEmpty(String key) {
        return getCollectionDb().queryBuilder().where(CollectionModelDao.Properties.Url.eq(key)).unique() == null;
    }

    public static void clear(String key) {
        getCollectionDb().deleteByKey(key);
    }

    public static void clear() {
        getCollectionDb().deleteAll();
    }
}
