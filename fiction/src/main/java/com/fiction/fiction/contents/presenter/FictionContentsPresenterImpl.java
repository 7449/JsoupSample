package com.fiction.fiction.contents.presenter;

import com.fiction.fiction.contents.model.FictionContentsModel;
import com.fiction.fiction.contents.view.FictionContentsView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeListManager;
import com.fiction.manager.JsoupKswListManager;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/1/8.
 */

public class FictionContentsPresenterImpl extends PresenterImplCompat<List<FictionContentsModel>, FictionContentsView>
        implements FictionContentsPresenter {

    private String type = ApiConfig.Type.ZW_81;

    public FictionContentsPresenterImpl(FictionContentsView view) {
        super(view);
    }

    @Override
    public void startContents(String url, String type) {
        this.type = type;
        netWork(url);
    }


    @Override
    public List<FictionContentsModel> getT(Document document) {
        switch (type) {
            case ApiConfig.Type.KSW:
                return JsoupKswListManager.get(document).getKswListContents();
            case ApiConfig.Type.BI_QU_GE:
                return JsoupBiQuGeListManager.get(document).getBiQuGeListContents();
            case ApiConfig.Type.ZW_81:
                return JsoupZwListManager.get(document).getZwListContents();
            default:
                return new ArrayList<>();
        }
    }
}
