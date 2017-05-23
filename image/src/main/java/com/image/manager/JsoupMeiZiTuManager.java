package com.image.manager;

import android.support.annotation.NonNull;

import com.image.image.detail.model.ImageDetailModel;
import com.image.image.list.model.ImageListModel;

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

    public List<ImageListModel> getImageList() {
        List<ImageListModel> listModels = new ArrayList<>();
        ImageListModel imageListModel;
        Elements select = document.select("div.pic");
        for (Element element : select) {
            imageListModel = new ImageListModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a[href]").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<ImageDetailModel> getImageDetail() {

        List<ImageDetailModel> list = new ArrayList<>();
        ImageDetailModel imageDetailModel;

        Elements select = document.select("div#picture").select("img");
        for (Element element : select) {
            imageDetailModel = new ImageDetailModel();
            imageDetailModel.url = element.attr("src");
            list.add(imageDetailModel);
        }

        return list;
    }
}
