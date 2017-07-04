package com.image.mvp.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.BasePresenterImpl;
import com.image.manager.JsoupTagManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 02/07/2017.
 */

public class TagImageListPresenterImpl extends BasePresenterImpl<List<ImageModel>, ViewManager.TagImageListView> implements PresenterManager.TagImageListPresenter {

    public TagImageListPresenterImpl(ViewManager.TagImageListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String url) {
        netWork(url);
    }

    @Override
    public List<ImageModel> getT(Document document) {
        return JsoupTagManager.get(document).getTagList();
    }
}
