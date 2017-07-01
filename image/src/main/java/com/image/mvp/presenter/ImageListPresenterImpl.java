package com.image.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupDoubanManager;
import com.image.manager.JsoupKKManager;
import com.image.manager.JsoupMMManager;
import com.image.manager.JsoupMZiTuManager;
import com.image.manager.JsoupMeiZiTuManager;
import com.image.manager.UrlManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class ImageListPresenterImpl extends BasePresenterImpl<List<ImageModel>, ViewManager.ImageListView> implements PresenterManager.ImageListPresenter {


    public ImageListPresenterImpl(ViewManager.ImageListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int id, int page) {
        netWork(UrlManager.getListUrl(view.getType(), id, page));
    }

    @Override
    public List<ImageModel> getT(Document document) {
        if (view == null) {
            return new ArrayList<>();
        }
        switch (view.getType()) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return JsoupDoubanManager.get(document).getImageList();
            case ApiConfig.Type.KK:
                return JsoupKKManager.get(document).getImageList();
            case ApiConfig.Type.M_ZI_TU:
                return JsoupMZiTuManager.get(document).getImageList();
            case ApiConfig.Type.MM:
                return JsoupMMManager.get(document).getImageList();
            case ApiConfig.Type.MEIZITU:
                return JsoupMeiZiTuManager.get(document).getImageList();
            default:
                return new ArrayList<>();
        }
    }
}
