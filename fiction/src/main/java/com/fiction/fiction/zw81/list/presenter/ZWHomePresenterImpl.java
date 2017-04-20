package com.fiction.fiction.zw81.list.presenter;

import com.fiction.fiction.zw81.list.model.ZWHomeModel;
import com.fiction.fiction.zw81.list.view.ZWHomeView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupZwHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class ZWHomePresenterImpl extends PresenterImplCompat<List<ZWHomeModel>, ZWHomeView> implements ZWHomePresenter {

    public ZWHomePresenterImpl(ZWHomeView view) {
        super(view);
    }

    @Override
    public void netWork() {
        netWork(ApiConfig.ZW81_URL);
    }

    @Override
    public List<ZWHomeModel> getT(Document document) {
        return JsoupZwHomeManager.get(document).getZwHome();
    }
}
