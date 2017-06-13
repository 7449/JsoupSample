package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.K567JsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/6/9.
 */

public class K567ListPresenterImpl extends BasePresenterImpl<List<MovieModel>, ViewManager.K567ListView> implements PresenterManager.K567ListPresenter {


    public K567ListPresenterImpl(ViewManager.K567ListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        String s = UIUtils.getString(R.string.k567_suffix);
        String format = String.format(ApiConfig.K_567_URL + s, page);
        netWork(format);
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return K567JsoupManager.get(document).getList();
    }
}
