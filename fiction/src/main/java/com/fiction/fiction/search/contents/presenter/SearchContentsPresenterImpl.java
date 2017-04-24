package com.fiction.fiction.search.contents.presenter;

import com.fiction.fiction.search.contents.model.SearchContentsModel;
import com.fiction.fiction.search.contents.view.SearchContentsView;
import com.fiction.manager.JsoupSearchManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class SearchContentsPresenterImpl extends PresenterImplCompat<List<SearchContentsModel>, SearchContentsView> implements SearchContentsPresenter {

    public SearchContentsPresenterImpl(SearchContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<SearchContentsModel> getT(Document document) {
        return JsoupSearchManager.get(document).get81Contents();
    }
}
