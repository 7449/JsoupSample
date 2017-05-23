package com.fiction.fiction.ksw.contents.presenter;

import com.fiction.fiction.ksw.contents.model.KswListContentsModel;
import com.fiction.fiction.ksw.contents.view.KswListContentsView;
import com.fiction.manager.JsoupKswListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class KswListContentsPresenterImpl extends PresenterImplCompat<List<KswListContentsModel>, KswListContentsView> implements KswListContentsPresenter {

    public KswListContentsPresenterImpl(KswListContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<KswListContentsModel> getT(Document document) {
        return JsoupKswListManager.get(document).getKswListContents();
    }
}
