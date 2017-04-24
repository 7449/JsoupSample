package com.fiction.fiction.ksw.contents.presenter;

import com.fiction.fiction.ksw.contents.model.KswHomeContentsModel;
import com.fiction.fiction.ksw.contents.view.KswHomeContentsView;
import com.fiction.manager.JsoupKswHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class KswHomeContentsPresenterImpl extends PresenterImplCompat<List<KswHomeContentsModel>, KswHomeContentsView> implements KswHomeContentsPresenter {

    public KswHomeContentsPresenterImpl(KswHomeContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<KswHomeContentsModel> getT(Document document) {
        return JsoupKswHomeManager.get(document).getKswContents();
    }
}
