package com.image.image.douban.list.presenter;

import com.framework.base.PresenterImplCompat;
import com.image.image.douban.list.model.DouBanListModel;
import com.image.image.douban.list.view.DouBanListView;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupDoubanManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class DouBanListPresenterImpl extends PresenterImplCompat<List<DouBanListModel>, DouBanListView> implements DouBanListPresenter {

    public DouBanListPresenterImpl(DouBanListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int id, int page) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(ApiConfig.DBMZ_URL + (id + 2) + ApiConfig.dou_ban_link + page, this),
                        this);
    }

    @Override
    public List<DouBanListModel> getT(Document document) {
        return JsoupDoubanManager.get(document).getImageList();
    }
}
