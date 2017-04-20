package com.fiction.fiction.search.list.presenter;

import android.text.TextUtils;

import com.fiction.db.SearchDb;
import com.fiction.fiction.search.list.model.SearchListModel;
import com.fiction.fiction.search.list.view.SearchListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupZwSearchManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class SearchListPresenterImpl extends PresenterImplCompat<List<SearchListModel>, SearchListView> implements SearchListPresenter {

    private String fictionName;

    public SearchListPresenterImpl(SearchListView view) {
        super(view);
    }

    @Override
    public void startSearch(String fictionName, int page) {
        this.fictionName = fictionName;
        if (!TextUtils.isEmpty(fictionName)) {
            netWork(ApiConfig.SEARCH_ZW81_URL + fictionName + "&p=" + page + ApiConfig.SEARCH_ZW81_SUFFIX);
        } else {
            view.fictionNameEmpty();
        }
    }

    @Override
    public void onNetWorkSuccess(List<SearchListModel> data) {
        super.onNetWorkSuccess(data);
        if (!TextUtils.isEmpty(fictionName)) {
            SearchDb.insert(fictionName);
        }
    }

    @Override
    public List<SearchListModel> getT(Document document) {
        return JsoupZwSearchManager.get(document).get81List();
    }
}
