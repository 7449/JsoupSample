package com.image.image.mzitu.detail.presenter;


import com.framework.base.mvp.PresenterImplCompat;
import com.image.image.mzitu.detail.model.MZiTuDetailModel;
import com.image.image.mzitu.detail.view.MZiTuDetailView;
import com.image.manager.JsoupMZiTuManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2016/7/28.
 */
public class MZiTuDetailPresenterImpl extends PresenterImplCompat<List<MZiTuDetailModel>, MZiTuDetailView>
        implements MZiTuDetailPresenter {

    public MZiTuDetailPresenterImpl(MZiTuDetailView view) {
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
    public List<MZiTuDetailModel> getT(Document document) {
        return JsoupMZiTuManager.get(document).getImageDetail();
    }
}
