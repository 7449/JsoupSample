package com.image.image.mm.detail.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.image.image.mm.detail.model.MMDetailModel;
import com.image.image.mm.detail.view.MMDetailView;
import com.image.manager.JsoupMMManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class MMDetailPresenterImpl extends PresenterImplCompat<List<MMDetailModel>, MMDetailView> implements MMDetailPresenter {
    public MMDetailPresenterImpl(MMDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url + "/1");
    }

    @Override
    public List<MMDetailModel> getT(Document document) {
        return JsoupMMManager.get(document).getImageDetail();
    }
}
