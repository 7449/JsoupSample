package com.movie.movie.xiaopian.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.XiaoPianJsoupManager;
import com.movie.movie.xiaopian.list.model.XiaoPianListModel;
import com.movie.movie.xiaopian.list.view.XiaoPianListView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianListPresenterImpl extends PresenterImplCompat<List<XiaoPianListModel>, XiaoPianListView> implements XiaoPianListPresenter {

    public XiaoPianListPresenterImpl(XiaoPianListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(getXiaoPianUrl(type, page), this),
                        this);
    }


    @Override
    public List<XiaoPianListModel> getT(Document document) {
        return XiaoPianJsoupManager.get(document).getXiaoPianList();
    }

    private String getXiaoPianUrl(int type, int page) {
        String[] stringArray = UIUtils.getStringArray(R.array.xiao_pian_suffix);
        if (page == 1) {
            return String.format(ApiConfig.XIAO_PIAN_URL + stringArray[type], "");
        } else {
            return String.format(ApiConfig.XIAO_PIAN_URL + stringArray[type], "_" + page);
        }
    }


}
