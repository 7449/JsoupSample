package com.movie.movie.piaohua.detail.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.PiaoHuaJsoupManager;
import com.movie.movie.piaohua.detail.model.PiaoHuaDetailModel;
import com.movie.movie.piaohua.detail.view.PiaoHuaDetailView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaDetailPresenterImpl extends PresenterImplCompat<PiaoHuaDetailModel, PiaoHuaDetailView> implements PiaoHuaDetailPresenter {

    public PiaoHuaDetailPresenterImpl(PiaoHuaDetailView view) {
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
    public PiaoHuaDetailModel getT(Document document) {
        return PiaoHuaJsoupManager.get(document).getDetail();
    }
}
