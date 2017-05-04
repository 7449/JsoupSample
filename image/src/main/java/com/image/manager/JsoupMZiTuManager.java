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

public class JsoupMZiTuManager {

    private static final String IMAGE_TITLE = "http://i.meizitu.net/";
    private static final String IMAGE_SUFFIX = "01.jpg";

    private Document document;

    private JsoupMZiTuManager(Document document) {
        this.document = document;
    }

    public static JsoupMZiTuManager get(@NonNull Document document) {
        return new JsoupMZiTuManager(document);
    }

    public List<ImageListModel> getImageList() {
        List<ImageListModel> listModels = new ArrayList<>();
        ImageListModel imageListModel;
        Elements select = document.select("#pins").select("a:has(img)");
        for (Element element : select) {
            imageListModel = new ImageListModel();
            imageListModel.setUrl(element.select("img").attr("data-original"));
            imageListModel.setDetailUrl(element.select("a").attr("href"));
            listModels.add(imageListModel);
        }
        return listModels;
    }

    public List<ImageDetailModel> getImageDetail() {

        List<ImageDetailModel> list = new ArrayList<>();
        ImageDetailModel imageDetailModel;
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
            imageDetailModel = new ImageDetailModel();
            if (i > 9) {
                imageDetailModel.setUrl(IMAGE_TITLE + year + "/" + moth + "/" + mark + i + ".jpg");
            } else {
                imageDetailModel.setUrl(IMAGE_TITLE + year + "/" + moth + "/" + mark + 0 + i + ".jpg");
            }
            list.add(imageDetailModel);
        }
        return list;
    }
}
