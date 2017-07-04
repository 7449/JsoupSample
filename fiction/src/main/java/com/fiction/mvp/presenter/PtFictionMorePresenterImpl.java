package com.fiction.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fiction.manager.JsoupPtFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 04/07/2017.
 */

public class PtFictionMorePresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.PtFictionMoreView> implements PresenterManager.PtFictionMorePresenter {

    public PtFictionMorePresenterImpl(ViewManager.PtFictionMoreView view) {
        super(view);
    }

    @Override
    public void netWork(@NonNull String url, int page) {

        if (TextUtils.isEmpty(url)) {
            onNetWorkError(new NullPointerException("url == null"));
            return;
        }
        netWork(url.replaceAll("1.html", page + ".html"));
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupPtFictionListManager.get(document).getList();
    }
}
