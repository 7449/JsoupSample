package com.magnetic.manager;

import android.support.annotation.NonNull;

import com.magnetic.mvp.model.MagneticModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/6/6.
 */

public class MaYiJsoupManager {
    private Document document;

    private MaYiJsoupManager(Document document) {
        this.document = document;
    }

    public static MaYiJsoupManager get(@NonNull Document document) {
        return new MaYiJsoupManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel magneticModel;
        Elements a = document.select("div.search-item");
        for (Element element : a) {
            magneticModel = new MagneticModel();
            magneticModel.title = element.select("div.item-title").text();
            magneticModel.url = element.select("div.item-bar").select("a[href]").eq(0).attr("href");
            listModels.add(magneticModel);
        }
        return listModels;
    }
}
