package com.magnetic.manager;

import com.framework.utils.UIUtils;
import com.magnetic.mvp.model.CollectionModel;
import com.magnetic.mvp.model.CollectionModelDao;
import com.magnetic.mvp.model.DaoMaster;
import com.magnetic.mvp.model.DaoSession;
import com.magnetic.mvp.model.SearchModel;
import com.magnetic.mvp.model.SearchModelDao;

import java.util.List;

/**
 * by y on 2017/6/21.
 */

public class DBManager {
    private static final String SQL_NAME = "magnetic";

    private DBManager() {
    }

    private static class SessionHolder {
        private static final DaoSession daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(UIUtils.getContext(), SQL_NAME, null).getWritableDatabase()).newSession();
    }

    public static boolean isCollectionEmpty(String key) {
        return SessionHolder.daoSession.getCollectionModelDao().queryBuilder().where(CollectionModelDao.Properties.Url.eq(key)).unique() == null;
    }

    public static List<CollectionModel> getCollectionContent() {
        return SessionHolder.daoSession.getCollectionModelDao().loadAll();
    }

    public static void insertCollection(String markName, String url) {
        SessionHolder.daoSession.getCollectionModelDao().insert(new CollectionModel(markName, url));
    }

    public static void clearCollection(String key) {
        SessionHolder.daoSession.getCollectionModelDao().deleteByKey(key);
    }

    public static void clearCollection() {
        SessionHolder.daoSession.getCollectionModelDao().deleteAll();
    }


    public static boolean isSearchEmpty(String key) {
        return SessionHolder.daoSession.getSearchModelDao().queryBuilder().where(SearchModelDao.Properties.SearchContent.eq(key)).unique() == null;
    }

    public static List<SearchModel> getSearchContent() {
        return SessionHolder.daoSession.getSearchModelDao().loadAll();
    }

    public static void insertSearch(String markName) {
        SessionHolder.daoSession.getSearchModelDao().insert(new SearchModel(markName));
    }

    public static void clearSearch(String key) {
        SessionHolder.daoSession.getSearchModelDao().deleteByKey(key);
    }

    public static void clearSearch() {
        SessionHolder.daoSession.getSearchModelDao().deleteAll();
    }
}
