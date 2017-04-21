package com.image.manager;

import android.support.annotation.NonNull;

import com.image.search.detail.model.SearchDetailModel;
import com.image.search.list.model.SearchListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class JsoupSearchManager {
    private static final String IMAGE_TITLE = "http://i.meizitu.net/";
    private static final String IMAGE_SUFFIX = "01.jpg";
    private Document document;

    private JsoupSearchManager(Document document) {
        this.document = document;
    }

    public static JsoupSearchManager get(@NonNull Document document) {
        return new JsoupSearchManager(document);
    }

    public List<SearchListModel> getImageList() {
        List<SearchListModel> listModels = new ArrayList<>();
        SearchListModel imageListModel;
        Elements select = document.select("#pins").select("a:has(img)");
        for (Element element : select) {
            imageListModel = new SearchListModel();
            imageListModel.url = element.select("img").attr("data-original");
            imageListModel.detailUrl = element.select("a").attr("href");
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<SearchDetailModel> getImageDetail() {
        List<SearchDetailModel> list = new ArrayList<>();
        SearchDetailModel imageDetailModel;
        //先获取第一页的image url 并且去掉后缀 01.jpg
        String url = document.select("div.main-image").select("img").attr("src").replace(IMAGE_SUFFIX, "");

        //获取重要的url拼接 tag
        String[] split = url.replace(IMAGE_TITLE, "").split("/");
        String year = split[0]; //年份
        String moth = split[1]; //月份
        String mark = split[2]; //tag

        //获取这个类型的图片总数，循环拼Url，不去循环请求网址了，这样会造成大量的数据请求，图片的URL由自己拼接
        Elements select = document.select("div.pagenavi").select("a");
        Integer integer = Integer.valueOf(select.eq(select.size() - 2).text());
        for (Integer i = 1; i <= integer; i++) {
            imageDetailModel = new SearchDetailModel();
            if (i > 9) {
                imageDetailModel.url = IMAGE_TITLE + year + "/" + moth + "/" + mark + i + ".jpg";
            } else {
                imageDetailModel.url = IMAGE_TITLE + year + "/" + moth + "/" + mark + 0 + i + ".jpg";
            }
            list.add(imageDetailModel);
        }
        return list;
    }
}
