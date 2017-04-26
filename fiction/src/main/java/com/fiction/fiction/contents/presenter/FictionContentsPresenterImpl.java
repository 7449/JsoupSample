package com.fiction.fiction.contents.presenter;

import com.fiction.fiction.contents.model.FictionContentsModel;
import com.fiction.fiction.contents.view.FictionContentsView;
import com.fiction.manager.JsoupFictionListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class FictionContentsPresenterImpl extends PresenterImplCompat<List<FictionContentsModel>, FictionContentsView>
        implements FictionContentsPresenter {

    public FictionContentsPresenterImpl(FictionContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url, String type) {
        netWork(url);
    }


    @Override
    public List<FictionContentsModel> getT(Document document) {
        return JsoupFictionListManager.get(document).getContents();
    }
}
