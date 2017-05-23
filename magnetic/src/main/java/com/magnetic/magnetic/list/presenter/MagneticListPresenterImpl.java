package com.magnetic.magnetic.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.StringUtils;
import com.magnetic.magnetic.list.model.MagneticListModel;
import com.magnetic.magnetic.list.view.MagneticListView;
import com.magnetic.manager.ApiConfig;
import com.magnetic.manager.JsoupMagneticManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/28
 */

public class MagneticListPresenterImpl extends PresenterImplCompat<List<MagneticListModel>, MagneticListView> implements MagneticListPresenter {


    public MagneticListPresenterImpl(MagneticListView view) {
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
    public List<MagneticListModel> getT(Document document) {
        return JsoupMagneticManager.get(document).getList();
    }
}
