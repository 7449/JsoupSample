package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.mvp.model.MovieModel;

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

    public List<MovieModel> getDy2018List() {
        List<MovieModel> list = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("a.ulink[title]");
        for (Element element : select) {
            model = new MovieModel();
            model.title = element.text();
            model.detailUrl = element.attr("abs:href");
            list.add(model);
        }
        return list;
    }

    public MovieModel getDy2018Detail() {
        MovieModel model = new MovieModel();
        model.title = document.select("div.title_all").text();
        model.message = document.select("div#Zoom").html();
        return model;
    }
}
