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

public class NiMaJsoupManager {
    private Document document;

    private NiMaJsoupManager(Document document) {
        this.document = document;
    }

    public static NiMaJsoupManager get(@NonNull Document document) {
        return new NiMaJsoupManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel magneticModel;
        Elements a = document.select("td.x-item");
        for (Element element : a) {
            magneticModel = new MagneticModel();
            magneticModel.title = element.select("a:not(.rel)").text();
            magneticModel.url = element.select("a[rel]").attr("href");
            listModels.add(magneticModel);
        }
        return listModels;
    }
}
