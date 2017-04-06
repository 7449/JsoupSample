package com.fiction.fiction.search.list.presenter;

import android.text.TextUtils;

import com.fiction.db.SearchDb;
import com.fiction.fiction.search.list.model.SearchListModel;
import com.fiction.fiction.search.list.view.SearchListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.Jsoup81Manager;
import com.framework.base.PresenterImplCompat;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

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
    public void startSearch(final String fictionName, final int page) {
        this.fictionName = fictionName;
        if (!TextUtils.isEmpty(fictionName)) {
            RxJsoupNetWork
                    .getInstance()
                    .getApi(
                            RxJsoupNetWork.onSubscribe(ApiConfig.BASE_81 + fictionName + "&p=" + page + ApiConfig.SUFFIX_81, this),
                            this);
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
        return Jsoup81Manager.get(document).get81List();
    }
}
