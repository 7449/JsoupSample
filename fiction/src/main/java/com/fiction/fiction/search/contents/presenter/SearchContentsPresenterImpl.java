package com.fiction.fiction.search.contents.presenter;

import com.fiction.fiction.search.contents.model.SearchContentsModel;
import com.fiction.fiction.search.contents.view.SearchContentsView;
import com.fiction.manager.Jsoup81Manager;
import com.framework.base.mvp.PresenterImplCompat;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

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
    public void startContents(final String url) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(url, this),
                        this);
    }

    @Override
    public List<SearchContentsModel> getT(Document document) {
        return Jsoup81Manager.get(document).get81Contents();
    }
}
