package com.fiction.fiction.list.presenter;

import com.fiction.R;
import com.fiction.fiction.list.model.FictionListModel;
import com.fiction.fiction.list.view.FictionListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionListManager;
import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class FictionListPresenterImpl extends PresenterImplCompat<List<FictionListModel>, FictionListView> implements FictionListPresenter {

    public FictionListPresenterImpl(FictionListView view) {
        super(view);
    }

    @Override
    public void netWork(String type, int tabPosition) {
        switch (type) {
            case ApiConfig.Type.KSW:
                netWork(ApiConfig.KSW_URL + UIUtils.INSTANCE.getStringArray(R.array.tab_ksw_suffix)[tabPosition - 1]);
                break;
            case ApiConfig.Type.BI_QU_GE:
                netWork(ApiConfig.BI_QU_GE_URL + UIUtils.INSTANCE.getStringArray(R.array.tab_bi_qu_ge_suffix)[tabPosition - 1]);
                break;
            case ApiConfig.Type.ZW_81:
                netWork(ApiConfig.ZW81_URL + UIUtils.INSTANCE.getStringArray(R.array.tab_zw_suffix)[tabPosition - 1]);
                break;
        }
    }

    @Override
    public List<FictionListModel> getT(Document document) {
        return JsoupFictionListManager.get(document).getList();
    }
}
