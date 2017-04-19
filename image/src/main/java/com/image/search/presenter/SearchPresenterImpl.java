package com.image.search.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.PresenterImplCompat;
import com.image.manager.JsoupSearchManager;
import com.image.search.model.SearchModel;
import com.image.search.view.SearchView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class SearchPresenterImpl extends PresenterImplCompat<List<SearchModel>, SearchView> implements SearchPresenter {

    public SearchPresenterImpl(SearchView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String searchType, @NonNull String content, int page) {
        netWork(String.format("http://www.mzitu.com/search/%s/page/%s/", content, page));
    }

    @Override
    public List<SearchModel> getT(Document document) {
        return JsoupSearchManager.get(document).getImageList();
    }
}
