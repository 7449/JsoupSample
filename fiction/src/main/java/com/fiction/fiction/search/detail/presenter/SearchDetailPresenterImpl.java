package com.fiction.fiction.search.detail.presenter;

import com.fiction.fiction.search.detail.model.SearchDetailModel;
import com.fiction.fiction.search.detail.view.SearchDetailView;
import com.fiction.manager.Jsoup81Manager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class SearchDetailPresenterImpl extends PresenterImplCompat<SearchDetailModel, SearchDetailView> implements SearchDetailPresenter {

    public SearchDetailPresenterImpl(SearchDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url) {
        netWork(url);
    }

    @Override
    public SearchDetailModel getT(Document document) {
        return Jsoup81Manager.get(document).get81Detail();
    }
}
