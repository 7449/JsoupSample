package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.search.list.model.SearchListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupSearchManager {
    private Document document;

    private JsoupSearchManager(Document document) {
        this.document = document;
    }

    public static JsoupSearchManager get(@NonNull Document document) {
        return new JsoupSearchManager(document);
    }

    public List<SearchListModel> get81List() {
        List<SearchListModel> list = new ArrayList<>();
        SearchListModel fictionNameModel;
        Elements select = document.select("div.result-game-item");
        for (Element element : select) {
            fictionNameModel = new SearchListModel();
            fictionNameModel.setUrl(element.select("img.result-game-item-pic-link-img").attr("src"));
            fictionNameModel.setTitle(element.select("a.result-game-item-title-link").attr("title"));
            fictionNameModel.setDetailUrl(element.select("a.result-game-item-title-link").attr("href"));
            fictionNameModel.setMessage(element.select("p.result-game-item-desc").text());
            list.add(fictionNameModel);
        }
        return list;
    }
}
