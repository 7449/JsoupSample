package com.fiction.search.presenter;

import android.text.TextUtils;

import com.fiction.db.SearchDb;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.Jsoup81Manager;
import com.fiction.search.model.SearchModel;
import com.fiction.search.view.SearchView;
import com.framework.base.PresenterImplCompat;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class SearchPresenterImpl extends PresenterImplCompat<List<SearchModel>, SearchView> implements SearchPresenter {

    private String fictionName;

    public SearchPresenterImpl(SearchView view) {
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
    public void onNetWorkSuccess(List<SearchModel> data) {
        super.onNetWorkSuccess(data);
        if (!TextUtils.isEmpty(fictionName)) {
            SearchDb.insert(fictionName);
        }
    }

    @Override
    public List<SearchModel> getT(Document document) {
        return Jsoup81Manager.get(document).get81List();
    }
}
