package com.image.image.mzitu.list.presenter;


import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.mzitu.list.model.MZiTuListModel;
import com.image.image.mzitu.list.view.MZiTuListView;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupMZiTuManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2016/7/28.
 */
public class MZiTuListPresenterImpl extends PresenterImplCompat<List<MZiTuListModel>, MZiTuListView>
        implements MZiTuListPresenter {

    public MZiTuListPresenterImpl(MZiTuListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int id, final int page) {
        netWork(ApiConfig.M_ZI_TU_URL +
                UIUtils.getStringArray(R.array.mzitu_array_suffix)[id] +
                ApiConfig.SLASH +
                ApiConfig.PAGE +
                ApiConfig.SLASH + page);
    }

    @Override
    public List<MZiTuListModel> getT(Document document) {
        return JsoupMZiTuManager.get(document).getImageList();
    }
}
