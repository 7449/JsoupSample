package com.image.manager;

import android.support.annotation.NonNull;

import com.framework.utils.MatcherUtils;
import com.image.image.kk.detail.model.KKDetailModel;
import com.image.image.kk.list.model.KKListModel;

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

    public List<KKListModel> getImageList() {
        List<KKListModel> listModels = new ArrayList<>();
        KKListModel imageListModel;
        Elements select = document.select("div.pic-block").select("a");
        for (Element element : select) {
            imageListModel = new KKListModel();
            imageListModel.url = element.select("img").attr("src");
            imageListModel.detailUrl = element.select("a").attr("abs:href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<String> getKKDetailUrl() {
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

    public List<KKDetailModel> getKKDetailImage() {
        List<KKDetailModel> list = new ArrayList<>();
        Elements img = document.select("img.lazy-img");
        KKDetailModel imageDetailModel;
        for (Element element : img) {
            imageDetailModel = new KKDetailModel();
            String attr = element.attr("data-original");
            imageDetailModel.url = attr.replace("219_300", "800_0");
            list.add(imageDetailModel);
        }
        return list;
    }
}
