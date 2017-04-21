package com.fiction.fiction.biquge.list.presenter;

import com.fiction.R;
import com.fiction.fiction.biquge.list.model.BiQuGeListModel;
import com.fiction.fiction.biquge.list.view.BiQuGeListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeListManager;
import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class BiQuGeListPresenterImpl extends PresenterImplCompat<List<BiQuGeListModel>, BiQuGeListView> implements BiQuGeListPresenter {

    public BiQuGeListPresenterImpl(BiQuGeListView view) {
        super(view);
    }

    @Override
    public void netWork(int tabPosition) {
        String suffix = UIUtils.getStringArray(R.array.tab_bi_qu_ge_suffix)[tabPosition - 1];
        netWork(ApiConfig.BI_QU_GE_URL + suffix);
    }

    @Override
    public List<BiQuGeListModel> getT(Document document) {
        return JsoupBiQuGeListManager.get(document).getBiqugeList();
    }
}
