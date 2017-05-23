package com.magnetic.mvp.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.StringUtils;
import com.magnetic.manager.ApiConfig;
import com.magnetic.manager.JsoupMagneticManager;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/28
 */

public class MagneticListPresenterImpl extends PresenterImplCompat<List<MagneticModel>, ViewManager.MagneticListView> implements PresenterManager.MagneticListPresenter {


    public MagneticListPresenterImpl(ViewManager.MagneticListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String content, int tabPosition, int page) {

        String url = null;

        switch (tabPosition) {
            case 0:
                url = String.format(ApiConfig.BT_CHERRY_URL, content, page);
                break;
            case 1:
                url = String.format(ApiConfig.BT_URLS_URL, StringUtils.getString(content, "~"), page);
                break;
            case 2:
                url = String.format(ApiConfig.BT_CILISOU_URL, content, page - 1);
                break;
        }

        netWork(url);
    }

    @Override
    public List<MagneticModel> getT(Document document) {
        return JsoupMagneticManager.get(document).getList();
    }
}
