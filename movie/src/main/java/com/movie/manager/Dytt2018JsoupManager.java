package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.movie.dy2018.detail.model.Dy2018DetailModel;
import com.movie.movie.dy2018.list.model.Dy2018ListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class Dytt2018JsoupManager {


    private Document document;

    private Dytt2018JsoupManager(Document document) {
        this.document = document;
    }

    public static Dytt2018JsoupManager get(@NonNull Document document) {
        return new Dytt2018JsoupManager(document);
    }

    public List<Dy2018ListModel> getDy2018List() {
        List<Dy2018ListModel> list = new ArrayList<>();
        Dy2018ListModel model;
        Elements select = document.select("a.ulink[title]");
        for (Element element : select) {
            model = new Dy2018ListModel();
            model.setTitle(element.text());
            model.setDetailUrl(element.attr("abs:href"));
            list.add(model);
        }
        return list;
    }

    public Dy2018DetailModel getDy2018Detail() {
        Dy2018DetailModel model = new Dy2018DetailModel();
        model.setTitle(document.select("div.title_all").text());
        model.setMessage(document.select("div#Zoom").html());
        return model;
    }
}
