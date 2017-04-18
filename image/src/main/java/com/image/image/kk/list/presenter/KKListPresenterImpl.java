package com.image.image.kk.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.kk.list.model.KKListModel;
import com.image.image.kk.list.view.KKListView;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupKKManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/6.
 */

public class KKListPresenterImpl extends PresenterImplCompat<List<KKListModel>, KKListView>
        implements KKListPresenter {

    public KKListPresenterImpl(KKListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int id, final int page) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(String.format(ApiConfig.KK_URL + UIUtils.getStringArray(R.array.kk_array_suffix)[id], page), this),
                        this);
    }

    @Override
    public List<KKListModel> getT(Document document) {
        return JsoupKKManager.get(document).getImageList();
    }
}

