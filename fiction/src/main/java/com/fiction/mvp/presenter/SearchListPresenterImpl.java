package com.fiction.mvp.presenter;


import android.text.TextUtils;

import com.fiction.db.SearchDb;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupSearchManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class SearchListPresenterImpl extends PresenterImplCompat<List<FictionModel>, ViewManager.SearchListView> implements PresenterManager.SearchListPresenter {

    private String fictionName;

    public SearchListPresenterImpl(ViewManager.SearchListView view) {
        super(view);
    }

    @Override
    public void startSearch(String fictionName, int page, String searchType) {
        this.fictionName = fictionName;
        if (!TextUtils.isEmpty(fictionName)) {
            netWork(getSearchUrl(searchType, fictionName, page));
        } else {
            view.fictionNameEmpty();
        }
    }

    @Override
    public void onNetWorkSuccess(List<FictionModel> data) {
        super.onNetWorkSuccess(data);
        if (!TextUtils.isEmpty(fictionName)) {
            SearchDb.insert(fictionName);
        }
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupSearchManager.get(document).get81List();
    }

    /**
     * 这种小说网站好像调用的搜索网站都一样......
     */
    private String getSearchUrl(String searchType, String fictionName, int page) {
        switch (searchType) {
            case ApiConfig.Type.ZW_81:
                return ApiConfig.SEARCH_URL + fictionName + "&p=" + page + ApiConfig.SEARCH_SUFFIX;
            case ApiConfig.Type.BI_QU_GE:
                return ApiConfig.SEARCH_URL + fictionName + "&p=" + page + ApiConfig.SEARCH_SUFFIX;
            default:
                return ApiConfig.SEARCH_URL + fictionName + "&p=" + page + ApiConfig.SEARCH_SUFFIX;
        }
    }
}
