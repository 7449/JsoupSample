package com.movie.movie.dytt.detail.presenter;

import com.framework.base.PresenterImplCompat;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.detail.model.DyttVideoDetailModel;
import com.movie.movie.dytt.detail.view.DyttVideoDetailView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/23
 */

public class DyttVideoDetailPresenterImpl
        extends PresenterImplCompat<DyttVideoDetailModel, DyttVideoDetailView>
        implements DyttVideoDetailPresenter {

    public DyttVideoDetailPresenterImpl(DyttVideoDetailView view) {
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
    public DyttVideoDetailModel getT(Document document) {
        return DyttJsoupManager.get(document).getDyttVideoDetail();
    }
}
