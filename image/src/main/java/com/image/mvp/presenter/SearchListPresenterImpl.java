package com.image.mvp.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.BasePresenterImpl;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupSearchManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class SearchListPresenterImpl extends BasePresenterImpl<List<ImageModel>, ViewManager.SearchListView>
        implements PresenterManager.SearchListPresenter {

    public SearchListPresenterImpl(ViewManager.SearchListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String searchType, @NonNull String content, int page) {
        switch (searchType) {
            case ApiConfig.Type.M_ZI_TU:
                netWork(String.format(ApiConfig.SearchUrl.M_ZI_TU_SEARCH_URL, content, page));
                break;
            default:
                break;
        }
    }

    @Override
    public List<ImageModel> getT(Document document) {
        return JsoupSearchManager.get(document).getImageList();
    }
}
