package com.movie.movie.dytt.more.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.more.model.DyttVideoMoreModel;
import com.movie.movie.dytt.more.view.DyttVideoMoreView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttVideoMorePresenterImpl extends PresenterImplCompat<List<DyttVideoMoreModel>, DyttVideoMoreView>
        implements DyttVideoMorePresenter {
    private static final String BASE_URL = "http://www.ygdy8.net/html/";

    public DyttVideoMorePresenterImpl(DyttVideoMoreView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int placeType, int page) {
        netWork(getMoreUrl(type, placeType, page));
    }

    @Override
    public List<DyttVideoMoreModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttMoreVideoList();
    }


    private String getMoreUrl(int type, int placeType, int page) {
        if (placeType == ApiConfig.Type.DYTT_CHOSEN_TYPE) {
            String[] stringArray = UIUtils.INSTANCE.getStringArray(R.array.dytt_chosen_suffix);
            return type == 0 ? String.format(BASE_URL + stringArray[type], page) : String.format(BASE_URL + stringArray[type - 1], page);
        } else {
            return String.format(BASE_URL + UIUtils.INSTANCE.getStringArray(R.array.dytt_xl_suffix)[type], page);
        }
    }
}
