package com.image.image.list.presenter;


import com.framework.base.PresenterImplCompat;
import com.image.image.list.model.ImageListModel;
import com.image.image.list.view.ImageView;
import com.image.manager.CorrectUtils;
import com.image.manager.JsoupMZiTuManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2016/7/28.
 */
public class ImagePresenterImpl extends PresenterImplCompat<List<ImageListModel>, ImageView>
        implements ImagePresenter {

    public ImagePresenterImpl(ImageView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int id, final int page, final String type) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(CorrectUtils.getListUrl(id, page, type), this),
                        this);
    }

    @Override
    public List<ImageListModel> getT(Document document) {
        return JsoupMZiTuManager.getImageList(view.getType(), document);
    }
}
