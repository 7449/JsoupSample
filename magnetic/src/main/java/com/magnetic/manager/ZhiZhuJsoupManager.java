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

public class ZhiZhuJsoupManager {
    private Document document;

    private ZhiZhuJsoupManager(Document document) {
        this.document = document;
    }

    public static ZhiZhuJsoupManager get(@NonNull Document document) {
        return new ZhiZhuJsoupManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel magneticModel;
        Elements a = document.select("div.list").select("a[href]:not(.desc):not(.asc)");
        for (Element element : a) {
            if (!element.text().equals("点击搜索")) {
                magneticModel = new MagneticModel();
                magneticModel.title = element.text();
                magneticModel.url = element.select("a[href]").attr("abs:href");
                listModels.add(magneticModel);
            }
        }
        return listModels;
    }

    public MagneticModel getDetail() {
        MagneticModel magneticModel = new MagneticModel();
        magneticModel.url = document.select("div.mag1").text();
        return magneticModel;
    }
}
