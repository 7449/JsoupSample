package com.movie.movie.piaohua.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.PiaoHuaJsoupManager;
import com.movie.movie.piaohua.list.model.PiaoHuaListModel;
import com.movie.movie.piaohua.list.view.PiaoHuaListView;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaListPresenterImpl extends PresenterImplCompat<List<PiaoHuaListModel>, PiaoHuaListView>
        implements PiaoHuaListPresenter {

    public PiaoHuaListPresenterImpl(PiaoHuaListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int position, int page) {
        RxJsoupNetWork
                .getInstance()
                .getApi(
                        RxJsoupNetWork.onSubscribe(getPiaoHuaUrl(position, page), this),
                        this);
    }

    @Override
    public List<PiaoHuaListModel> getT(Document document) {
        return PiaoHuaJsoupManager.get(document).getList();
    }


    private String getPiaoHuaUrl(int position, int page) {
        String[] stringArray = UIUtils.getStringArray(R.array.piao_hua_suffix);
        return String.format(ApiConfig.PIAO_HUA_URL + stringArray[position], page);
    }
}
