package com.image.search.detail.presenter;


import com.framework.base.mvp.PresenterImplCompat;
import com.image.manager.JsoupSearchManager;
import com.image.search.detail.model.SearchDetailModel;
import com.image.search.detail.view.SearchDetailView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2016/7/28.
 */
public class SearchDetailPresenterImpl extends PresenterImplCompat<List<SearchDetailModel>, SearchDetailView>
        implements SearchDetailPresenter {

    public SearchDetailPresenterImpl(SearchDetailView view) {
        super(view);
    }


    @Override
    public void netWorkRequest(String searchType, String url) {
        netWork(url);
    }

    @Override
    public List<SearchDetailModel> getT(Document document) {
        return JsoupSearchManager.get(document).getImageDetail();
    }
}
