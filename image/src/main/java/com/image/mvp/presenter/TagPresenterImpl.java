package com.image.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.image.manager.ApiConfig;
import com.image.manager.JsoupTagManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 02/07/2017.
 */

public class TagPresenterImpl extends BasePresenterImpl<List<ImageModel>, ViewManager.TagView> implements PresenterManager.TagPresenter {

    public TagPresenterImpl(ViewManager.TagView view) {
        super(view);
    }

    @Override
    public void netWorkRequest() {
        switch (view.getTabPosition()) {
            case 0:
                netWork(ApiConfig.TAG_421);
                break;
        }
    }

    @Override
    public List<ImageModel> getT(Document document) {
        if (view == null) {
            return new ArrayList<>();
        }
        switch (view.getTabPosition()) {
            case 0:
                return JsoupTagManager.get(document).getTag();
        }
        return new ArrayList<>();
    }
}
