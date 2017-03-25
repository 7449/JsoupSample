package com.movie.movie.dytt.list.presenter;

import com.framework.base.PresenterImplCompat;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.list.model.DyttNewModel;
import com.movie.movie.dytt.list.view.DyttNewView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttNewPresenterImpl extends PresenterImplCompat<List<DyttNewModel>, DyttNewView> implements DyttNewPresenter {


    public DyttNewPresenterImpl(DyttNewView view) {
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
    public List<DyttNewModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttNewList();
    }
}
