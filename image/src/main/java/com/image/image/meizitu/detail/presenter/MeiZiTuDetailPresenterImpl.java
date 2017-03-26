package com.image.image.meizitu.detail.presenter;

import com.framework.base.PresenterImplCompat;
import com.image.image.meizitu.detail.model.MeiZiTuDetailModel;
import com.image.image.meizitu.detail.view.MeiZiTuDetailView;
import com.image.manager.JsoupMeiZiTuManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class MeiZiTuDetailPresenterImpl extends PresenterImplCompat<List<MeiZiTuDetailModel>, MeiZiTuDetailView> implements MeiZiTuDetailPresenter {


    public MeiZiTuDetailPresenterImpl(MeiZiTuDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(url, this),
                        this);
    }

    @Override
    public List<MeiZiTuDetailModel> getT(Document document) {
        return JsoupMeiZiTuManager.get(document).getImageDetail();
    }
}
