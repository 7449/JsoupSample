package com.image.search.list.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.PresenterImplCompat;
import com.image.manager.JsoupSearchManager;
import com.image.search.list.model.SearchListModel;
import com.image.search.list.view.SearchListView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class SearchListPresenterImpl extends PresenterImplCompat<List<SearchListModel>, SearchListView> implements SearchListPresenter {

    public SearchListPresenterImpl(SearchListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String searchType, @NonNull String content, int page) {
        netWork(String.format("http://www.mzitu.com/search/%s/page/%s/", content, page));
    }

    @Override
    public List<SearchListModel> getT(Document document) {
        return JsoupSearchManager.get(document).getImageList();
    }
}
