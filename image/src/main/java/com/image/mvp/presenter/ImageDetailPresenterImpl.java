package com.image.mvp.presenter;


import android.support.annotation.NonNull;

import com.framework.base.mvp.BasePresenterImpl;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupDoubanManager;
import com.image.manager.JsoupKKManager;
import com.image.manager.JsoupMMManager;
import com.image.manager.JsoupMZiTuManager;
import com.image.manager.JsoupMeiZiTuManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;
import io.reactivex.observers.DisposableObserver;

/**
 * by y on 2016/7/28.
 */
public class ImageDetailPresenterImpl extends BasePresenterImpl<List<ImageModel>, ViewManager.ImageDetailView>
        implements PresenterManager.ImageDetailPresenter {


    public ImageDetailPresenterImpl(ViewManager.ImageDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        switch (view.getType()) {
            case ApiConfig.Type.MM:
                netWork(url + "/1");
                break;

            case ApiConfig.Type.KK:
                RxJsoupNetWork.getInstance().getApi(KK_URL_TAG, url,
                        new RxJsoupNetWorkListener<List<String>>() {
                            @Override
                            public void onNetWorkStart() {
                                if (view != null) {
                                    view.showProgress();
                                }
                            }

                            @Override
                            public void onNetWorkError(Throwable e) {

                            }

                            @Override
                            public void onNetWorkComplete() {

                            }

                            @Override
                            public void onNetWorkSuccess(List<String> data) {

                                if (data != null) {
                                    List<Observable<List<ImageModel>>> list = new ArrayList<>();
                                    for (int i = 0; i < data.size(); i++) {
                                        String s = data.get(i);
                                        list.add(Observable
                                                .create(
                                                        e -> {
                                                            Document document = RxJsoupNetWork.getT(s);
                                                            e.onNext(JsoupKKManager.get(document).getImageDetail());
                                                            e.onComplete();
                                                        }));
                                    }
                                    RxJsoupNetWork
                                            .getInstance()
                                            .getApi(KK_DATA_TAG,
                                                    Observable.mergeArray(list.toArray(new Observable[]{}))
                                                    , new DisposableObserver<List<ImageModel>>() {
                                                        @Override
                                                        public void onNext(@NonNull List<ImageModel> o) {
                                                            if (view != null) {
                                                                view.netWorkSuccess(o);
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(@NonNull Throwable e) {

                                                        }

                                                        @Override
                                                        public void onComplete() {
                                                            if (view != null) {
                                                                view.hideProgress();
                                                            }
                                                        }
                                                    });
                                }
                            }

                            @Override
                            public List<String> getT(Document document) {
                                return JsoupKKManager.get(document).getDetailUrl();
                            }
                        });

                break;

            default:
                netWork(url);
                break;
        }
    }

    @Override
    public List<ImageModel> getT(Document document) {
        if (view == null) {
            return new ArrayList<>();
        }
        switch (view.getType()) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return JsoupDoubanManager.get(document).getImageDetail();
            case ApiConfig.Type.M_ZI_TU:
                return JsoupMZiTuManager.get(document).getImageDetail();
            case ApiConfig.Type.MM:
                return JsoupMMManager.get(document).getImageDetail();
            case ApiConfig.Type.MEIZITU:
                return JsoupMeiZiTuManager.get(document).getImageDetail();
            default:
                return new ArrayList<>();
        }
    }
}
