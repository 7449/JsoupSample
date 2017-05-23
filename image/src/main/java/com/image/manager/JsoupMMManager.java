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

public class JsoupMMManager {


    private static final String mmImageTitle = "http://img.mmjpg.com/";

    private Document document;

    private JsoupMMManager(Document document) {
        this.document = document;
    }

    public static JsoupMMManager get(@NonNull Document document) {
        return new JsoupMMManager(document);
    }

    public List<ImageModel> getImageList() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        Elements select = document.select("div.pic").select("li");
        for (Element element : select) {
            imageListModel = new ImageModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }


    public List<ImageModel> getImageDetail() {
        List<ImageModel> list = new ArrayList<>();
        String html = document.select("script[type]").html();
        String[] split = html.substring(html.indexOf("[") + 1, html.indexOf("]")).split(",");
        Integer integer = Integer.valueOf(split[2]);
        ImageModel imageDetailModel;
        for (Integer i = 1; i <= integer; i++) {
            imageDetailModel = new ImageModel();
            imageDetailModel.url = mmImageTitle + split[0] + "/" + split[1] + "/" + i + ".jpg";
            list.add(imageDetailModel);
        }
        return list;
    }
}
