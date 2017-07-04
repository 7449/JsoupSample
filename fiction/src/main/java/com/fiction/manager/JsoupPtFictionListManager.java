package com.fiction.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fiction.mvp.model.FictionModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 03/07/2017.
 */

public class JsoupPtFictionListManager {
    private Document document;

    private JsoupPtFictionListManager(Document document) {
        this.document = document;
    }

    public static JsoupPtFictionListManager get(@NonNull Document document) {
        return new JsoupPtFictionListManager(document);
    }

    public List<FictionModel> getList() {
        List<FictionModel> list = new ArrayList<>();
        FictionModel fictionModel;
        Elements select = document.select("table.grid").select("tr:has(a)");
        for (Element element : select) {
            fictionModel = new FictionModel();
            fictionModel.detailUrl = element.select("td.odd").select("a").attr("abs:href");
            fictionModel.title = element.select("td.odd").select("a").text();
            list.add(fictionModel);
        }
        return list;
    }

    public List<FictionModel> getContents() {
        List<FictionModel> list = new ArrayList<>();
        FictionModel fictionModel;
        Elements select = document.select("div.centent").select("li").select("a[href]");
        for (Element element : select) {
            String attr = element.attr("abs:href");
            if (!TextUtils.isEmpty(attr)) {
                fictionModel = new FictionModel();
                fictionModel.detailUrl = attr;
                fictionModel.title = element.text();
                list.add(fictionModel);
            }
        }
        if (!list.isEmpty()) {
            list.remove(0);
        }
        return list;
    }
}
