package com.magnetic.manager;

import android.support.annotation.NonNull;

import com.magnetic.mvp.model.MagneticModel;
import com.socks.library.KLog;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/6/15.
 */

public class DSJsoupManager {
    private Document document;

    private DSJsoupManager(Document document) {
        this.document = document;
    }

    public static DSJsoupManager get(@NonNull Document document) {
        return new DSJsoupManager(document);
    }

    public List<MagneticModel> getList() {
        List<MagneticModel> listModels = new ArrayList<>();
        MagneticModel magneticModel;
        Elements a = document.select("ul.mlist").select("li");
        for (Element element : a) {
            magneticModel = new MagneticModel();
            magneticModel.title = element.select("div.T1").text();
            magneticModel.url = element.select("div.dInfo").select("a[href]").eq(0).attr("href");
            KLog.i(magneticModel.url);
            listModels.add(magneticModel);
        }
        return listModels;
    }
}
