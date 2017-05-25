package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.XiaoPianJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianListPresenterImpl extends BasePresenterImpl<List<MovieModel>, ViewManager.XiaoPianListView> implements PresenterManager.XiaoPianListPresenter {

    public XiaoPianListPresenterImpl(ViewManager.XiaoPianListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        netWork(getXiaoPianUrl(type, page));
    }


    @Override
    public List<MovieModel> getT(Document document) {
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
