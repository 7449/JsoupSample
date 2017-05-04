package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.movie.piaohua.detail.model.PiaoHuaDetailModel;
import com.movie.movie.piaohua.list.model.PiaoHuaListModel;

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


    public List<PiaoHuaListModel> getList() {
        List<PiaoHuaListModel> listModels = new ArrayList<>();
        PiaoHuaListModel model;
        Elements select = document.select("div#list").select("a[class]");
        for (Element element : select) {
            model = new PiaoHuaListModel();
            model.setTitle(element.select("img").attr("alt"));
            model.setUrl(element.select("img").attr("src"));
            model.setDetailUrl(element.attr("abs:href"));
            listModels.add(model);
        }
        return listModels;
    }

    public PiaoHuaDetailModel getDetail() {
        PiaoHuaDetailModel model = new PiaoHuaDetailModel();
        model.setTitle(document.select("div#show").text());
        model.setMessage(document.select("div#showinfo").html());
        return model;
    }
}
