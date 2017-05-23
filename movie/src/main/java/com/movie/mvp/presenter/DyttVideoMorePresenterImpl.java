package com.movie.mvp.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttVideoMorePresenterImpl extends PresenterImplCompat<List<MovieModel>, ViewManager.DyttVideoMoreView>
        implements PresenterManager.DyttVideoMorePresenter {
    private static final String BASE_URL = "http://www.ygdy8.net/html/";

    public DyttVideoMorePresenterImpl(ViewManager.DyttVideoMoreView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int placeType, int page) {
        netWork(getMoreUrl(type, placeType, page));
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttMoreVideoList();
    }


    private String getMoreUrl(int type, int placeType, int page) {
        if (placeType == ApiConfig.Type.DYTT_CHOSEN_TYPE) {
            String[] stringArray = UIUtils.getStringArray(R.array.dytt_chosen_suffix);
            return type == 0 ? String.format(BASE_URL + stringArray[type], page) : String.format(BASE_URL + stringArray[type - 1], page);
        } else {
            return String.format(BASE_URL + UIUtils.getStringArray(R.array.dytt_xl_suffix)[type], page);
        }
    }
}
