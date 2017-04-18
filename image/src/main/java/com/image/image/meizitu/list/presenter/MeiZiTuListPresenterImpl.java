package com.image.image.meizitu.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.meizitu.list.model.MeiZiTuListModel;
import com.image.image.meizitu.list.view.MeiZiTuListView;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupMeiZiTuManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class MeiZiTuListPresenterImpl extends PresenterImplCompat<List<MeiZiTuListModel>, MeiZiTuListView> implements MeiZiTuListPresenter {
    public MeiZiTuListPresenterImpl(MeiZiTuListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(ApiConfig.MEIZITU_URL + String.format(UIUtils.getStringArray(R.array.meizitu_array_suffix)[type], page), this),
                        this);
    }

    @Override
    public List<MeiZiTuListModel> getT(Document document) {
        return JsoupMeiZiTuManager.get(document).getImageList();
    }
}
