package com.fiction.fiction.biquge.contents.presenter;

import com.fiction.fiction.biquge.contents.model.BiQuGeHomeContentsModel;
import com.fiction.fiction.biquge.contents.view.BiQuGeHomeContentsView;
import com.fiction.manager.JsoupBiQuGeHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class BiQuGeHomeContentsPresenterImpl extends PresenterImplCompat<List<BiQuGeHomeContentsModel>, BiQuGeHomeContentsView> implements BiQuGeHomeContentsPresenter {

    public BiQuGeHomeContentsPresenterImpl(BiQuGeHomeContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<BiQuGeHomeContentsModel> getT(Document document) {
        return JsoupBiQuGeHomeManager.get(document).getBiQugeContents();
    }
}
