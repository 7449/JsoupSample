package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.zw81.list.model.ZWHomeModel;
import com.socks.library.KLog;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupZwHomeManager {

    public static final int TYPE_HEADER = 0; //首页四个大图小说
    public static final int TYPE_PUSH = 1;   //上期强推
    public static final int TYPE_CENTER = 2;   //中间6个小说区
    public static final int TYPE_RECENT = 3;   //最近更新小说列表
    public static final int TYPE_ADD = 4;   //最新入库小说


    private Document document;

    private JsoupZwHomeManager(Document document) {
        this.document = document;
    }

    public static JsoupZwHomeManager get(@NonNull Document document) {
        return new JsoupZwHomeManager(document);
    }

    public List<ZWHomeModel> getZwHome() {
        List<ZWHomeModel> list = new ArrayList<>();
        initHeader(list);
        return list;
    }

    /**
     * 获取首页四个小说
     */
    private void initHeader(List<ZWHomeModel> list) {
        ZWHomeModel zwHomeModel;
        Elements select = document.select("div.item");
        for (Element element : select) {
            zwHomeModel = new ZWHomeModel();
            zwHomeModel.title = element.select("img[src]").attr("alt");
            zwHomeModel.url = element.select("img[src]").attr("src");
            zwHomeModel.detailUrl = element.select("a:has(img)").attr("abs:href");
            zwHomeModel.message = element.select("dd").text();
            zwHomeModel.type = TYPE_HEADER;
            list.add(zwHomeModel);
        }

        initPush(list);
    }

    /**
     * 获取上期强推
     */
    private void initPush(List<ZWHomeModel> list) {
        ZWHomeModel zwHomeModel;
        Elements select = document.select("div.r").eq(0).select("a[href]");
        for (Element element : select) {
            zwHomeModel = new ZWHomeModel();
            zwHomeModel.title = element.text();
            zwHomeModel.detailUrl = element.attr("abs:href");
            zwHomeModel.type = TYPE_PUSH;
            list.add(zwHomeModel);
        }

        initCenterHeader(list);
    }

    /**
     * 获取中间6个小说区头部
     */
    private void initCenterHeader(List<ZWHomeModel> list) {
        ZWHomeModel zwHomeModel;
        Elements select = document.select("div.content");
        for (int i = 0; i < select.size(); i++) {
            zwHomeModel = new ZWHomeModel();
            if (i == 0) {
                Elements topSelect = select.get(i).select("div.top");
                zwHomeModel.url = topSelect.select("img[src]").attr("src");
                zwHomeModel.title = topSelect.select("img[src]").attr("alt");
                zwHomeModel.detailUrl = topSelect.select("a:has(img)").attr("abs:href");
                zwHomeModel.message = topSelect.select("dd").text();
                zwHomeModel.type = TYPE_HEADER;
                list.add(zwHomeModel);
            } else {
                initCenter(list, select.get(i).select("li:has(a)"));
            }
        }
    }

    /**
     * 获取小说中间6个区详情
     */
    private void initCenter(List<ZWHomeModel> list, Elements elements) {
        ZWHomeModel zwHomeModel;
        Elements a = elements.select("a");
        for (Element element : a) {
            zwHomeModel = new ZWHomeModel();
            zwHomeModel.title = element.text();
            zwHomeModel.detailUrl = element.attr("abs:href");
            zwHomeModel.type = TYPE_CENTER;
            list.add(zwHomeModel);
        }

        initRecent(list);
    }

    /**
     * 最近更新小说列表
     */
    private void initRecent(List<ZWHomeModel> list) {
        ZWHomeModel zwHomeModel;
        Elements select = document.select("div#newscontent").select("div.l").select("li");
        KLog.i(select);
        for (Element element : select) {
            zwHomeModel = new ZWHomeModel();
//            zwHomeModel.title = element.text();
//            zwHomeModel.detailUrl = element.attr("abs:href");
            zwHomeModel.type = TYPE_RECENT;
            list.add(zwHomeModel);
        }

        initAdd(list);
    }

    /**
     * 获取最新入库小说
     */
    private void initAdd(List<ZWHomeModel> list) {
        ZWHomeModel zwHomeModel;
        Elements select = document.select("div.r").eq(1).select("a[href]");
        for (Element element : select) {
            zwHomeModel = new ZWHomeModel();
            zwHomeModel.title = element.text();
            zwHomeModel.detailUrl = element.attr("abs:href");
            zwHomeModel.type = TYPE_ADD;
            list.add(zwHomeModel);
        }
    }
}
