package com.fiction.fiction.ksw.list.presenter;

import com.fiction.R;
import com.fiction.fiction.ksw.list.model.KswListModel;
import com.fiction.fiction.ksw.list.view.KswListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupKswListManager;
import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class KswListPresenterImpl extends PresenterImplCompat<List<KswListModel>, KswListView> implements KswListPresenter {

    public KswListPresenterImpl(KswListView view) {
        super(view);
    }

    @Override
    public void netWork(int tabPosition) {
        String suffix = UIUtils.getStringArray(R.array.tab_ksw_suffix)[tabPosition - 1];
        netWork(ApiConfig.KSW_URL + suffix);
    }

    @Override
    public List<KswListModel> getT(Document document) {
        return JsoupKswListManager.get(document).getKswList();
    }
}
