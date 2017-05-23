package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.mvp.model.MovieModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/18
 */

public class PiaoHuaJsoupManager {

    private Document document;

    private PiaoHuaJsoupManager(Document document) {
        this.document = document;
    }

    public static PiaoHuaJsoupManager get(@NonNull Document document) {
        return new PiaoHuaJsoupManager(document);
    }


    public List<MovieModel> getList() {
        List<MovieModel> listModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("div#list").select("a[class]");
        for (Element element : select) {
            model = new MovieModel();
            model.title = element.select("img").attr("alt");
            model.url = element.select("img").attr("src");
            model.detailUrl = element.attr("abs:href");
            listModels.add(model);
        }
        return listModels;
    }

    public MovieModel getDetail() {
        MovieModel model = new MovieModel();
        model.title = document.select("div#show").text();
        model.message = document.select("div#showinfo").html();
        return model;
    }
}
