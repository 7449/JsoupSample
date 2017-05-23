package com.image.image.detail.presenter;


import com.framework.base.mvp.PresenterImplCompat;
import com.image.image.detail.model.ImageDetailModel;
import com.image.image.detail.view.ImageDetailView;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupDoubanManager;
import com.image.manager.JsoupKKManager;
import com.image.manager.JsoupMMManager;
import com.image.manager.JsoupMZiTuManager;
import com.image.manager.JsoupMeiZiTuManager;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

/**
 * by y on 2016/7/28.
 */
public class ImageDetailPresenterImpl extends PresenterImplCompat<List<ImageDetailModel>, ImageDetailView>
        implements ImageDetailPresenter {

    private String type = ApiConfig.Type.DOU_BAN_MEI_ZI;

    public ImageDetailPresenterImpl(ImageDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String type, String url) {
        this.type = type;
        switch (type) {
            case ApiConfig.Type.MM:
                netWork(url + "/1");
                break;

            case ApiConfig.Type.KK:


                RxJsoupNetWork.getInstance().getApi(url,
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
                                for (int i = 0; i < data.size(); i++) {
                                    netWork(data.get(i));
                                    if (i == data.size() - 1) {
                                        view.reverse();
                                    }
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
    public List<ImageDetailModel> getT(Document document) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return JsoupDoubanManager.get(document).getImageDetail();
            case ApiConfig.Type.M_ZI_TU:
                return JsoupMZiTuManager.get(document).getImageDetail();
            case ApiConfig.Type.MM:
                return JsoupMMManager.get(document).getImageDetail();
            case ApiConfig.Type.MEIZITU:
                return JsoupMeiZiTuManager.get(document).getImageDetail();
            case ApiConfig.Type.KK:
                return JsoupKKManager.get(document).getImageDetail();
            default:
                return new ArrayList<>();
        }
    }
}
