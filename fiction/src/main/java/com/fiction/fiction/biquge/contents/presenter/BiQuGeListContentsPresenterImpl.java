package com.fiction.fiction.biquge.contents.presenter;

import com.fiction.fiction.biquge.contents.model.BiQuGeListContentsModel;
import com.fiction.fiction.biquge.contents.view.BiQuGeListContentsView;
import com.fiction.manager.JsoupBiQuGeListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class BiQuGeListContentsPresenterImpl extends PresenterImplCompat<List<BiQuGeListContentsModel>, BiQuGeListContentsView> implements BiQuGeListContentsPresenter {

    public BiQuGeListContentsPresenterImpl(BiQuGeListContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<BiQuGeListContentsModel> getT(Document document) {
        return JsoupBiQuGeListManager.get(document).getBiQuGeListContents();
    }
}
