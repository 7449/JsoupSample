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
        int temp = 0;
        switch (type) {
            case 0:
                temp = page;
                break;
            case 1:
                temp = page + 2;
                break;
            case 2:
                temp = page + 9;
                break;
            case 3:
                temp = page + 14;
                break;
            case 4:
                temp = page + 19;
                break;
            case 5:
                temp = page + 25;
                break;
            case 6:
                temp = page + 36;
                break;
            case 7:
                temp = page + 47;
                break;
            case 8:
                temp = page + 58;
                break;
            case 9:
                temp = page + 79;
                break;
            case 10:
                temp = page + 129;
                break;
        }
        String format = String.format(ApiConfig.K_567_URL + UIUtils.getString(R.string.k567_suffix), temp);
        netWork(format);
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return K567JsoupManager.get(document).getList();
    }
}
