package com.image.manager;

import com.framework.utils.UIUtils;
import com.image.mvp.model.CollectionModel;
import com.image.mvp.model.CollectionModelDao;
import com.image.mvp.model.DaoMaster;
import com.image.mvp.model.DaoSession;

import java.util.List;


/**
 * by y on 2017/6/21.
 */

public class DBManager {
    private static final String SQL_NAME = "image";

    private DBManager() {
    }

    public static boolean isEmpty(String key) {
        return SessionHolder.daoSession.getCollectionModelDao().queryBuilder().where(CollectionModelDao.Properties.Url.eq(key)).unique() == null;
    }

    public static List<CollectionModel> getCollectionAll() {
        return SessionHolder.daoSession.getCollectionModelDao().loadAll();
    }

    public static void insert(String url) {
        SessionHolder.daoSession.getCollectionModelDao().insert(new CollectionModel(url));
    }

    public static void clear(String key) {
        SessionHolder.daoSession.getCollectionModelDao().deleteByKey(key);
    }

    public static void clear() {
        SessionHolder.daoSession.getCollectionModelDao().deleteAll();
    }

    private static class SessionHolder {
        private static final DaoSession daoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(UIUtils.getContext(), SQL_NAME, null).getWritableDatabase()).newSession();
    }
}
