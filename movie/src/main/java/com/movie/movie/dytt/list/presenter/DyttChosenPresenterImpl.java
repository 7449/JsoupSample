package com.movie.movie.dytt.list.presenter;

import com.framework.base.PresenterImplCompat;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.list.model.DyttChosenModel;
import com.movie.movie.dytt.list.view.DyttChosenView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttChosenPresenterImpl extends PresenterImplCompat<List<DyttChosenModel>, DyttChosenView>
        implements DyttChosenPresenter {


    public DyttChosenPresenterImpl(DyttChosenView view) {
        super(view);
    }

    @Override
    public void netWorkRequest() {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(ApiConfig.DYTT_URL, this),
                        this);
    }

    @Override
    public List<DyttChosenModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttChosenList();
    }
}
