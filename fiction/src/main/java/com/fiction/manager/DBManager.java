package com.fiction.manager;

import com.fiction.mvp.model.DaoMaster;
import com.fiction.mvp.model.DaoSession;
import com.fiction.mvp.model.MarkModel;
import com.fiction.mvp.model.MarkModelDao;
import com.framework.utils.UIUtils;

import java.util.List;

/**
 * by y on 2017/6/21.
 */

public class DBManager {
    private DBManager() {
    }

    private static final String SQL_NAME = "fiction";

    private static class SessionHolder {
        private static final DaoSession daoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(UIUtils.getContext(), SQL_NAME, null).getWritableDatabase()).newSession();
    }

    public static boolean isEmpty(String key) {
        return SessionHolder.daoSession.getMarkModelDao().queryBuilder().where(MarkModelDao.Properties.FictionName.eq(key)).unique() == null;
    }

    public static List<MarkModel> getFictionMarkAll() {
        return SessionHolder.daoSession.getMarkModelDao().loadAll();
    }

    public static void insert(String markName) {
        SessionHolder.daoSession.getMarkModelDao().insert(new MarkModel(markName));
    }

    public static void clear(String key) {
        SessionHolder.daoSession.getMarkModelDao().deleteByKey(key);
    }

    public static void clear() {
        SessionHolder.daoSession.getMarkModelDao().deleteAll();
    }
}
