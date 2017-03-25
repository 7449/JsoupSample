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

public class JsoupManager {


    private JsoupManager() {
    }

    public static List<ImageListModel> getImageList(@NonNull String type, @NonNull Document document) {
        List<ImageListModel> listModels = new ArrayList<>();
        ImageListModel imageListModel;
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                Elements a = document.select("div.img_single").select("a");
                for (Element element : a) {
                    imageListModel = new ImageListModel();
                    imageListModel.url = element.select("img[class]").attr("src");
                    imageListModel.detailUrl = element.select("a[class]").attr("href");
                    listModels.add(imageListModel);
                }
                break;
            case ApiConfig.Type.M_ZI_TU:
                Elements select = document.select("#pins").select("a:has(img)");
                for (Element element : select) {
                    imageListModel = new ImageListModel();
                    imageListModel.url = element.select("img").attr("data-original");
                    imageListModel.detailUrl = element.select("a").attr("href");
                    listModels.add(imageListModel);
                }
                break;
        }
        return listModels;
    }

    public static List<ImageDetailModel> getImageDetail(@NonNull String type, @NonNull Document document) {
        List<ImageDetailModel> list = new ArrayList<>();
        ImageDetailModel imageDetailModel;
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI_DETAIL:
                Elements img = document.select("div.panel-body").select("img");
                for (Element element : img) {
                    imageDetailModel = new ImageDetailModel();
                    imageDetailModel.url = element.select("img[src]").attr("src");
                    list.add(imageDetailModel);
                }
                break;
            case ApiConfig.Type.M_ZI_TU_DETAIL:
                Elements mzituElements = document.select("div.main-image").select("img");
                for (Element element : mzituElements) {
                    imageDetailModel = new ImageDetailModel();
                    imageDetailModel.url = element.attr("src");
                    list.add(imageDetailModel);
                }
                break;
        }
        return list;
    }
}
