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

public class JsoupDoubanManager {

    private Document document;

    private JsoupDoubanManager(Document document) {
        this.document = document;
    }

    public static JsoupDoubanManager get(@NonNull Document document) {
        return new JsoupDoubanManager(document);
    }

    public List<ImageModel> getImageList() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        Elements a = document.select("div.img_single").select("a");
        for (Element element : a) {
            imageListModel = new ImageModel();
            imageListModel.url = element.select("img[class]").attr("src");
            imageListModel.detailUrl = element.select("a[class]").attr("href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<ImageModel> getImageDetail() {
        List<ImageModel> list = new ArrayList<>();
        ImageModel imageDetailModel;
        Elements img = document.select("div.panel-body").select("img");
        for (Element element : img) {
            imageDetailModel = new ImageModel();
            imageDetailModel.url = element.select("img[src]").attr("src");
            list.add(imageDetailModel);
        }
        return list;
    }
}
