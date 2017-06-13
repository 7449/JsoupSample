package com.movie.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.framework.utils.MatcherUtils;
import com.movie.mvp.model.MovieModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/18
 */

public class K567JsoupManager {

    private Document document;

    private K567JsoupManager(Document document) {
        this.document = document;
    }

    public static K567JsoupManager get(@NonNull Document document) {
        return new K567JsoupManager(document);
    }


    public List<MovieModel> getList() {
        List<MovieModel> listModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("div.thumbInside");
        for (Element element : select) {
            model = new MovieModel();
            model.title = element.select("a[href]").text();
            model.url = element.select("img").attr("src");
            model.detailUrl = element.select("a[href]").attr("abs:href");
            listModels.add(model);
        }
        return listModels;
    }

    public List<MovieModel> getDetail() {
        List<MovieModel> listModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("div.thumbBlock");
        String kDetailUlr = MatcherUtils.get567KDetailUlr(document.select("div.player").html());
        String playUrl = null;
        if (!TextUtils.isEmpty(kDetailUlr)) {
            playUrl = kDetailUlr.replaceAll(",", "").replaceAll("'", "");
        }
        for (Element element : select) {
            model = new MovieModel();
            model.playUrl = playUrl;
            model.title = element.select("a[href]").text();
            model.url = element.select("img").attr("src");
            model.detailUrl = element.select("a[href]").attr("abs:href");
            listModels.add(model);
        }
        return listModels;
    }
}
