package com.image.manager;

import android.support.annotation.NonNull;

import com.image.mvp.model.ImageModel;
import com.socks.library.KLog;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 02/07/2017.
 */

public class JsoupTagManager {
    private Document document;

    private JsoupTagManager(Document document) {
        this.document = document;
    }

    public static JsoupTagManager get(@NonNull Document document) {
        return new JsoupTagManager(document);
    }

    public List<ImageModel> getTag() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        Elements a = document.select("div.tag_list").select("a");
        for (Element element : a) {
            imageListModel = new ImageModel();
            imageListModel.url = element.attr("abs:href");
            imageListModel.title = element.text();
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<ImageModel> getTagList() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        KLog.i(document);
        Elements a = document.select("div.tag_list").select("a");
        for (Element element : a) {
            imageListModel = new ImageModel();
            imageListModel.url = element.attr("abs:href");
            imageListModel.title = element.text();
            listModels.add(imageListModel);
        }
        return listModels;
    }
}
