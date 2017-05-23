package com.image.collection;


import static com.image.collection.GreenDaoDbUtils.getCollectionDb;

/**
 * by y on 2017/3/26.
 */

public class CollectionUtils {
    public static void insert(String url) {
        getCollectionDb().insert(new CollectionModel(url));
    }

    public static void deleted(String key) {
        GreenDaoDbUtils.clear(key);
    }

    public static boolean isEmpty(String key) {
        return getCollectionDb().queryBuilder().where(CollectionModelDao.Properties.Url.eq(key)).unique() == null;
    }
}
