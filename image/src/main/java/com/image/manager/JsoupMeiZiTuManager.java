package com.image.manager;

import android.support.annotation.NonNull;

import com.image.mvp.model.ImageModel;

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

    public List<ImageModel> getImageList() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        Elements select = document.select("div.pic");
        for (Element element : select) {
            imageListModel = new ImageModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a[href]").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<ImageModel> getImageDetail() {

        List<ImageModel> list = new ArrayList<>();
        ImageModel imageDetailModel;

        Elements select = document.select("div#picture").select("img");
        for (Element element : select) {
            imageDetailModel = new ImageModel();
            imageDetailModel.url = element.attr("src");
            list.add(imageDetailModel);
        }

        return list;
    }
}
