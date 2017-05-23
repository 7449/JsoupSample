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

public class JsoupMMManager {


    private static final String mmImageTitle = "http://img.mmjpg.com/";

    private Document document;

    private JsoupMMManager(Document document) {
        this.document = document;
    }

    public static JsoupMMManager get(@NonNull Document document) {
        return new JsoupMMManager(document);
    }

    public List<ImageListModel> getImageList() {
        List<ImageListModel> listModels = new ArrayList<>();
        ImageListModel imageListModel;
        Elements select = document.select("div.pic").select("li");
        for (Element element : select) {
            imageListModel = new ImageListModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }


    public List<ImageDetailModel> getImageDetail() {
        List<ImageDetailModel> list = new ArrayList<>();
        String html = document.select("script[type]").html();
        String[] split = html.substring(html.indexOf("[") + 1, html.indexOf("]")).split(",");
        Integer integer = Integer.valueOf(split[2]);
        ImageDetailModel imageDetailModel;
        for (Integer i = 1; i <= integer; i++) {
            imageDetailModel = new ImageDetailModel();
            imageDetailModel.url = mmImageTitle + split[0] + "/" + split[1] + "/" + i + ".jpg";
            list.add(imageDetailModel);
        }

//        MMDetailModel imageDetailModel;
//        Elements mzituElements = document.select("div.content").select("img");
//        for (Element element : mzituElements) {
//            imageDetailModel = new MMDetailModel();
//            imageDetailModel.url = element.attr("src");
//            list.add(imageDetailModel);
//        }
        return list;
    }
}
