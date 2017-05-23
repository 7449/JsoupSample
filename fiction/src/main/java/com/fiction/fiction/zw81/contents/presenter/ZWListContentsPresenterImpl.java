package com.fiction.fiction.zw81.contents.presenter;

import com.fiction.fiction.zw81.contents.model.ZWListContentsModel;
import com.fiction.fiction.zw81.contents.view.ZWListContentsView;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class ZWListContentsPresenterImpl extends PresenterImplCompat<List<ZWListContentsModel>, ZWListContentsView> implements ZWListContentsPresenter {

    public ZWListContentsPresenterImpl(ZWListContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<ZWListContentsModel> getT(Document document) {
        return JsoupZwListManager.get(document).getZwListContents();
    }
}
