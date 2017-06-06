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

public class YingTaoJsoupManager {
    private Document document;

    private YingTaoJsoupManager(Document document) {
        this.document = document;
    }

    public static YingTaoJsoupManager get(@NonNull Document document) {
        return new YingTaoJsoupManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel magneticModel;
        Elements a = document.select("div.r");
        int size = a.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                Element element = a.get(i);
                magneticModel = new MagneticModel();
                magneticModel.title = element.select("a[class]").text();
                magneticModel.url = element.select("a:not(.link)").attr("href");
                listModels.add(magneticModel);
            }
        }
        return listModels;
    }
}
