package com.image.manager;

import android.support.annotation.NonNull;

import com.framework.utils.MatcherUtils;
import com.image.mvp.model.ImageModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class JsoupKKManager {


    private Document document;

    private JsoupKKManager(Document document) {
        this.document = document;
    }

    public static JsoupKKManager get(@NonNull Document document) {
        return new JsoupKKManager(document);
    }

    public List<ImageModel> getImageList() {
        List<ImageModel> listModels = new ArrayList<>();
        ImageModel imageListModel;
        Elements select = document.select("div.pic-block").select("a");
        for (Element element : select) {
            imageListModel = new ImageModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<String> getDetailUrl() {
        List<String> list = new ArrayList<>();
        Elements img = document.select("img.lazy-img");
        //获取总张数
        int count = MatcherUtils.getInt(document.select("p.ui-block-a").text());
        //有几页
        long round = Math.round((double) count / img.size());
        String url = document.select("div.pagination").select("div.ui-block-a").select("a").attr("abs:href").replace("1.html", "");
        for (long i = 1; i <= round; i++) {
            list.add(String.format(url + i, ".html"));
        }
        return list;
    }

    public List<ImageModel> getImageDetail() {
        List<ImageModel> list = new ArrayList<>();
        Elements img = document.select("img.lazy-img");
        ImageModel imageDetailModel;
        for (Element element : img) {
            imageDetailModel = new ImageModel();
            String attr = element.attr("data-original");
            imageDetailModel.url = attr.replace("219_300", "800_0");
            list.add(imageDetailModel);
        }
        return list;
    }
}
