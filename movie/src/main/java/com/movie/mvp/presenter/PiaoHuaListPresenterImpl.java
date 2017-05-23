package com.movie.mvp.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.PiaoHuaJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaListPresenterImpl extends PresenterImplCompat<List<MovieModel>, ViewManager.PiaoHuaListView>
        implements PresenterManager.PiaoHuaListPresenter {

    public PiaoHuaListPresenterImpl(ViewManager.PiaoHuaListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int position, int page) {
        netWork(getPiaoHuaUrl(position, page));
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return PiaoHuaJsoupManager.get(document).getList();
    }


    private String getPiaoHuaUrl(int position, int page) {
        String[] stringArray = UIUtils.getStringArray(R.array.piao_hua_suffix);
        return String.format(ApiConfig.PIAO_HUA_URL + stringArray[position], page);
    }
}
