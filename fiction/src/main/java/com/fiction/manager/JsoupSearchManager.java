package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.mvp.model.FictionModel;

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

    public List<FictionModel> get81List() {
        List<FictionModel> list = new ArrayList<>();
        FictionModel fictionNameModel;
        Elements select = document.select("div.result-game-item");
        for (Element element : select) {
            fictionNameModel = new FictionModel();
            fictionNameModel.url = element.select("img.result-game-item-pic-link-img").attr("src");
            fictionNameModel.title = element.select("a.result-game-item-title-link").attr("title");
            fictionNameModel.detailUrl = element.select("a.result-game-item-title-link").attr("href");
            fictionNameModel.message = element.select("p.result-game-item-desc").text();
            list.add(fictionNameModel);
        }
        return list;
    }
}
