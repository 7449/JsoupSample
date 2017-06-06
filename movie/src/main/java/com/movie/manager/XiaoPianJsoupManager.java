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

public class XiaoPianJsoupManager {


    private Document document;

    private XiaoPianJsoupManager(Document document) {
        this.document = document;
    }

    public static XiaoPianJsoupManager get(@NonNull Document document) {
        return new XiaoPianJsoupManager(document);
    }

    public List<MovieModel> getXiaoPianList() {
        List<MovieModel> listModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("a.ulink[title]");
        for (Element element : select) {
            model = new MovieModel();
            model.title = element.text();
            model.detailUrl = element.attr("abs:href");
            listModels.add(model);
        }
        return listModels;
    }
}
