package com.image.manager;

import android.support.annotation.NonNull;

import com.image.image.meizitu.detail.model.MeiZiTuDetailModel;
import com.image.image.meizitu.list.model.MeiZiTuListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class JsoupMeiZiTuManager {


    private Document document;

    private JsoupMeiZiTuManager(Document document) {
        this.document = document;
    }

    public static JsoupMeiZiTuManager get(@NonNull Document document) {
        return new JsoupMeiZiTuManager(document);
    }

    public List<MeiZiTuListModel> getImageList() {
        List<MeiZiTuListModel> listModels = new ArrayList<>();
        MeiZiTuListModel imageListModel;
        Elements select = document.select("div.pic");
        for (Element element : select) {
            imageListModel = new MeiZiTuListModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a[href]").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<MeiZiTuDetailModel> getImageDetail() {

        List<MeiZiTuDetailModel> list = new ArrayList<>();
        MeiZiTuDetailModel imageDetailModel;

        Elements select = document.select("div#picture").select("img");
        for (Element element : select) {
            imageDetailModel = new MeiZiTuDetailModel();
            imageDetailModel.url = element.attr("src");
            list.add(imageDetailModel);
        }

        return list;
    }
}
