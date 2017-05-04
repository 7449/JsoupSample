package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.movie.xiaopian.detail.model.XiaoPianDetailModel;
import com.movie.movie.xiaopian.list.model.XiaoPianListModel;

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

    public List<XiaoPianListModel> getXiaoPianList() {
        List<XiaoPianListModel> listModels = new ArrayList<>();
        XiaoPianListModel model;
        Elements select = document.select("a.ulink[title]");
        for (Element element : select) {
            model = new XiaoPianListModel();
            model.setTitle(element.text());
            model.setDetailUrl(element.attr("abs:href"));
            listModels.add(model);
        }
        return listModels;
    }

    public XiaoPianDetailModel getXiaoPianDetail() {
        XiaoPianDetailModel model = new XiaoPianDetailModel();
        model.setTitle(document.select("div.title_all").text());
        model.setMessage(document.select("div#Zoom").html());
        return model;
    }
}
