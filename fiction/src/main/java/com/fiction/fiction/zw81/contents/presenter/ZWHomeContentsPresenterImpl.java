package com.fiction.fiction.zw81.contents.presenter;

import com.fiction.fiction.zw81.contents.model.ZWHomeContentsModel;
import com.fiction.fiction.zw81.contents.view.ZWHomeContentsView;
import com.fiction.manager.JsoupZwHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class ZWHomeContentsPresenterImpl extends PresenterImplCompat<List<ZWHomeContentsModel>, ZWHomeContentsView> implements ZWHomeContentsPresenter {

    public ZWHomeContentsPresenterImpl(ZWHomeContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url) {
        netWork(url);
    }

    @Override
    public List<ZWHomeContentsModel> getT(Document document) {
        return JsoupZwHomeManager.get(document).getZwHomeContents();
    }
}
