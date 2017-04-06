package com.fiction.fiction.search.contents.presenter;

import com.fiction.fiction.search.contents.model.ContentsModel;
import com.fiction.fiction.search.contents.view.ContentsView;
import com.fiction.manager.Jsoup81Manager;
import com.framework.base.PresenterImplCompat;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class ContentsPresenterImpl extends PresenterImplCompat<List<ContentsModel>, ContentsView> implements ContentsPresenter {

    public ContentsPresenterImpl(ContentsView view) {
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
    public List<ContentsModel> getT(Document document) {
        return Jsoup81Manager.get(document).get81Contents();
    }
}
