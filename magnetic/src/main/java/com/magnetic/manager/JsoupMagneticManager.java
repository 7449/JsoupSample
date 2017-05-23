package com.magnetic.manager;

import android.support.annotation.NonNull;

import com.magnetic.mvp.model.MagneticModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class JsoupMagneticManager {


    private Document document;

    private JsoupMagneticManager(Document document) {
        this.document = document;
    }

    public static JsoupMagneticManager get(@NonNull Document document) {
        return new JsoupMagneticManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel listModel;
        Elements select = document.select("div.r:has(a)");
        for (Element element : select) {
            listModel = new MagneticModel();
            listModel.message = element.select("a").eq(0).text();
            listModel.url = element.select("a").eq(1).attr("href");
            listModels.add(listModel);
        }
        return listModels;
    }
}
