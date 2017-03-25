package com.image.manager;

import android.support.annotation.NonNull;

import com.image.image.meizitu.detail.model.MZiTuDetailModel;
import com.image.image.meizitu.list.model.MZiTuListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class JsoupMZiTuManager {


    private Document document;

    private JsoupMZiTuManager(Document document) {
        this.document = document;
    }

    public static JsoupMZiTuManager get(@NonNull Document document) {
        return new JsoupMZiTuManager(document);
    }

    public List<MZiTuListModel> getImageList() {
        List<MZiTuListModel> listModels = new ArrayList<>();
        MZiTuListModel imageListModel;
        Elements select = document.select("#pins").select("a:has(img)");
        for (Element element : select) {
            imageListModel = new MZiTuListModel();
            imageListModel.url = element.select("img").attr("data-original");
            imageListModel.detailUrl = element.select("a").attr("href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<MZiTuDetailModel> getImageDetail() {
        List<MZiTuDetailModel> list = new ArrayList<>();
        MZiTuDetailModel imageDetailModel;
        Elements mzituElements = document.select("div.main-image").select("img");
        for (Element element : mzituElements) {
            imageDetailModel = new MZiTuDetailModel();
            imageDetailModel.url = element.attr("src");
            list.add(imageDetailModel);
        }
        return list;
    }
}
