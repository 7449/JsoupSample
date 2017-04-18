package com.image.image.kk.detail.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.image.image.kk.detail.model.KKDetailModel;
import com.image.image.kk.detail.view.KKDetailView;
import com.image.manager.JsoupKKManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;
import com.rxjsoupnetwork.manager.RxJsoupNetWorkListener;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class KKDetailPresenterImpl extends PresenterImplCompat<List<KKDetailModel>, KKDetailView> implements KKDetailPresenter {
    public KKDetailPresenterImpl(KKDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {

        RxJsoupNetWork.getInstance().getApi(RxJsoupNetWork.onSubscribe(url,
                document -> JsoupKKManager.get(document).getKKDetailUrl()),
                new RxJsoupNetWorkListener<List<String>>() {
                    @Override
                    public void onNetWorkStart() {
                    }

                    @Override
                    public void onNetWorkError(Throwable e) {

                    }

                    @Override
                    public void onNetWorkComplete() {

                    }

                    @Override
                    public void onNetWorkSuccess(List<String> data) {
                        for (long i = 0; i < data.size(); i++) {
                            RxJsoupNetWork
                                    .getInstance()
                                    .getApi(
                                            RxJsoupNetWork.onSubscribe(data.get((int) i), KKDetailPresenterImpl.this),
                                            KKDetailPresenterImpl.this);
                            if (i == data.size() - 1) {
                                view.reverse();
                            }
                        }
                    }
                });

    }

    @Override
    public List<KKDetailModel> getT(Document document) {
        return JsoupKKManager.get(document).getKKDetailImage();
    }
}
