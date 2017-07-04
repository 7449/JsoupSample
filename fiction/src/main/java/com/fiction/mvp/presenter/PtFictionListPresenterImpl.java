package com.fiction.mvp.presenter;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupPtFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 03/07/2017.
 */

public class PtFictionListPresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.PtFictionListView> implements PresenterManager.PtFictionListPresenter {

    public PtFictionListPresenterImpl(ViewManager.PtFictionListView view) {
        super(view);
    }

    @Override
    public void netWork(int tabPosition, int page) {
        String format = String.format(ApiConfig.PIAO_TIAN_URL + UIUtils.getStringArray(R.array.pt_list_url_suffix)[tabPosition - 1], page);
        netWork(format);
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupPtFictionListManager.get(document).getList();
    }
}
