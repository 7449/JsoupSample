package com.image.manager;

import android.support.annotation.NonNull;

import com.image.search.list.model.SearchListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class JsoupSearchManager {

    private Document document;

    private JsoupSearchManager(Document document) {
        this.document = document;
    }

    public static JsoupSearchManager get(@NonNull Document document) {
        return new JsoupSearchManager(document);
    }

    public List<SearchListModel> getImageList() {
        List<SearchListModel> listModels = new ArrayList<>();
        SearchListModel imageListModel;
        Elements select = document.select("#pins").select("a:has(img)");
        for (Element element : select) {
            imageListModel = new SearchListModel();
            imageListModel.url = element.select("img").attr("data-original");
            imageListModel.detailUrl = element.select("a").attr("href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

}
