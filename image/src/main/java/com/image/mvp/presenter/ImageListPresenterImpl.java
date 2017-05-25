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

    private String type = ApiConfig.Type.DOU_BAN_MEI_ZI;

    public ImageListPresenterImpl(ViewManager.ImageListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String type, int id, int page) {
        this.type = type;
        netWork(UrlManager.getListUrl(type, id, page));
    }

    @Override
    public List<ImageModel> getT(Document document) {
        switch (type) {
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
