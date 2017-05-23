package com.fiction.fiction.zw81.list.presenter;

import com.fiction.R;
import com.fiction.fiction.zw81.list.model.ZWListModel;
import com.fiction.fiction.zw81.list.view.ZWListView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class ZWListPresenterImpl extends PresenterImplCompat<List<ZWListModel>, ZWListView> implements ZWListPresenter {

    public ZWListPresenterImpl(ZWListView view) {
        super(view);
    }

    @Override
    public void netWork(int tabPosition) {
        String suffix = UIUtils.getStringArray(R.array.tab_zw_suffix)[tabPosition - 1];
        netWork(ApiConfig.ZW81_URL + suffix);
    }

    @Override
    public List<ZWListModel> getT(Document document) {
        return JsoupZwListManager.get(document).getZwList();
    }
}
