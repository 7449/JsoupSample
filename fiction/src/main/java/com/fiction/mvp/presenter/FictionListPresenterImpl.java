package com.fiction.mvp.presenter;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class FictionListPresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.FictionListView> implements PresenterManager.FictionListPresenter {

    public FictionListPresenterImpl(ViewManager.FictionListView view) {
        super(view);
    }

    @Override
    public void netWork(String type, int tabPosition) {
        switch (type) {
            case ApiConfig.Type.KSW:
                netWork(ApiConfig.KSW_URL + UIUtils.getStringArray(R.array.tab_ksw_suffix)[tabPosition - 1]);
                break;
            case ApiConfig.Type.BI_QU_GE:
                netWork(ApiConfig.BI_QU_GE_URL + UIUtils.getStringArray(R.array.tab_bi_qu_ge_suffix)[tabPosition - 1]);
                break;
            case ApiConfig.Type.ZW_81:
                netWork(ApiConfig.ZW81_URL + UIUtils.getStringArray(R.array.tab_zw_suffix)[tabPosition - 1]);
                break;
        }
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupFictionListManager.get(document).getList();
    }
}
