package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.search.contents.model.ContentsModel;
import com.fiction.fiction.search.detail.model.DetailModel;
import com.fiction.fiction.search.list.model.SearchModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class Jsoup81Manager {
    private Document document;

    private Jsoup81Manager(Document document) {
        this.document = document;
    }

    public static Jsoup81Manager get(@NonNull Document document) {
        return new Jsoup81Manager(document);
    }

    public List<SearchModel> get81List() {
        List<SearchModel> list = new ArrayList<>();
        SearchModel fictionNameModel;
        for (Element element : document.select("div.result-game-item")) {
            fictionNameModel = new SearchModel();
            fictionNameModel.url = element.select("img.result-game-item-pic-link-img").attr("src");
            fictionNameModel.title = element.select("a.result-game-item-title-link").attr("title");
            fictionNameModel.detailUrl = element.select("a.result-game-item-title-link").attr("href");
            fictionNameModel.message = element.select("p.result-game-item-desc").text();
            list.add(fictionNameModel);
        }
        return list;
    }

    public List<ContentsModel> get81Contents() {
        List<ContentsModel> list = new ArrayList<>();
        ContentsModel contentsModel;
        for (Element element : document.select("#list").select("a")) {
            contentsModel = new ContentsModel();
            contentsModel.title = element.text();
            contentsModel.detailUrl = element.attr("abs:href");
            list.add(contentsModel);
        }
        return list;
    }

    public DetailModel get81Detail() {
        DetailModel detailModel = new DetailModel();
        Elements select = document.select("div.bottem2").select("a[href$=.html]");
        for (int i = 0; i < select.size(); i++) {
            switch (i) {
                case 0:
                    detailModel.onPage = select.get(i).attr("abs:href");
                    break;
                case 1:
                    detailModel.nextPage = select.get(i).attr("abs:href");
                    break;
            }
        }
        detailModel.title = document.select("div.bookname").select("h1").text();
        detailModel.message = document.select("#content").html();
        return detailModel;
    }
}
