package com.image.image.detail.presenter;


import com.framework.base.PresenterImplCompat;
import com.image.image.detail.model.ImageDetailModel;
import com.image.image.detail.view.ImageDetailView;
import com.image.manager.CorrectUtils;
import com.image.manager.JsoupManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2016/7/28.
 */
public class ImageDetailPresenterImpl extends PresenterImplCompat<List<ImageDetailModel>, ImageDetailView>
        implements ImageDetailPresenter {

    public ImageDetailPresenterImpl(ImageDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url, int page, String type) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(CorrectUtils.getDetailUrl(type, url, page), this),
                        this);
    }


    @Override
    public List<ImageDetailModel> getT(Document document) {
        return JsoupManager.getImageDetail(view.getType(), document);
    }
}
