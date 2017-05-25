package com.fiction.mvp.presenter;

import com.fiction.manager.JsoupFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

import java.util.List;


/**
 * by y on 2017/1/8.
 */

public class FictionContentsPresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.FictionContentsView>
        implements PresenterManager.FictionContentsPresenter {

    public FictionContentsPresenterImpl(ViewManager.FictionContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url, String type) {
        netWork(url);
    }


    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupFictionListManager.get(document).getContents();
    }
}
