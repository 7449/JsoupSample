package com.image.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framework.utils.MatcherUtils;
import com.image.mvp.model.ImageModel;
import com.socks.library.KLog;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class JsoupSearchManager {
    private static final String PAGE_ON = "«上一页";
    private Document document;

    private JsoupSearchManager(Document document) {
        this.document = document;
    }

    public static JsoupSearchManager get(@NonNull Document document) {
        return new JsoupSearchManager(document);
    }

    public List<ImageModel> getImageList() {
        int totalPage = 0;
        List<ImageModel> listModels = new ArrayList<>();
        String searchPage = document.select("div.nav-links").select("a").text();
        KLog.i(searchPage);
        if (TextUtils.isEmpty(searchPage)) {
            return listModels;
        } else {
            if (!searchPage.contains(PAGE_ON)) {
                totalPage = MatcherUtils.getIntHasSpace(searchPage);
                KLog.i(totalPage);
            }
            ImageModel imageListModel;
            Elements select = document.select("#pins").select("a:has(img)");
            for (Element element : select) {
                imageListModel = new ImageModel();
                imageListModel.url = element.select("img").attr("data-original");
                imageListModel.detailUrl = element.select("a").attr("href");
                if (!searchPage.contains(PAGE_ON)) {
                    imageListModel.totalPage = totalPage;
                }
                listModels.add(imageListModel);
            }
        }
        return listModels;
    }
}
