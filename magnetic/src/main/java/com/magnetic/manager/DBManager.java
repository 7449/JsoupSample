package com.magnetic.manager;

import com.framework.utils.UIUtils;
import com.magnetic.mvp.model.DaoMaster;
import com.magnetic.mvp.model.DaoSession;
import com.magnetic.mvp.model.SearchModel;
import com.magnetic.mvp.model.SearchModelDao;

import java.util.List;

/**
 * by y on 2017/6/21.
 */

public class DBManager {
    private DBManager() {
    }

    private static final String SQL_NAME = "magnetic";

    private static class SessionHolder {
        private static final DaoSession daoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(UIUtils.getContext(), SQL_NAME, null).getWritableDatabase()).newSession();
    }

    public static boolean isEmpty(String key) {
        return SessionHolder.daoSession.getSearchModelDao().queryBuilder().where(SearchModelDao.Properties.SearchContent.eq(key)).unique() == null;
    }

    public static List<SearchModel> getSearchContent() {
        return SessionHolder.daoSession.getSearchModelDao().loadAll();
    }

    public static void insert(String markName) {
        SessionHolder.daoSession.getSearchModelDao().insert(new SearchModel(markName));
    }

    public static void clear(String key) {
        SessionHolder.daoSession.getSearchModelDao().deleteByKey(key);
    }

    public static void clear() {
        SessionHolder.daoSession.getSearchModelDao().deleteAll();
    }
}
