package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.zw81.contents.model.ZWListContentsModel;
import com.fiction.fiction.zw81.detail.model.ZWListDetailModel;
import com.fiction.fiction.zw81.list.model.ZWListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupZwListManager {

    public static final int TYPE_HEADER = 0; //首页6个大图小说
    public static final int TYPE_UPDATE = 1;   //最近更新列表
    public static final int TYPE_ADD = 2;   //其他小说
    public static final int TYPE_TITLE = 3;

    private static final String TYPE_TITLE_LIST = "最近更新列表:";
    private static final String TYPE_TITLE_ADD = "好看的:";

    private Document document;

    private JsoupZwListManager(Document document) {
        this.document = document;
    }

    public static JsoupZwListManager get(@NonNull Document document) {
        return new JsoupZwListManager(document);
    }

    public List<ZWListModel> getZwList() {
        List<ZWListModel> list = new ArrayList<>();
        initHeader(list);
        return list;
    }

    /**
     * 获取首页六个小说
     */
    private void initHeader(List<ZWListModel> list) {
        ZWListModel zwHomeModel;
        Elements select = document.select("div.item");
        for (Element element : select) {
            zwHomeModel = new ZWListModel();
            zwHomeModel.title = element.select("img[src]").attr("alt");
            zwHomeModel.url = element.select("img[src]").attr("src");
            zwHomeModel.detailUrl = element.select("a:has(img)").attr("abs:href");
            zwHomeModel.message = element.select("dd").text();
            zwHomeModel.type = TYPE_HEADER;
            list.add(zwHomeModel);
        }
        initList(list);
    }

    /**
     * 最近更新小说列表
     */
    private void initList(List<ZWListModel> list) {
        ZWListModel pushTitle = new ZWListModel();
        pushTitle.title = TYPE_TITLE_LIST;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        ZWListModel zwListModel;
        Elements select = document.select("div#newscontent").select("div.l").select("span.s2").select("a");
        for (Element element : select) {
            zwListModel = new ZWListModel();
            zwListModel.title = element.text();
            zwListModel.detailUrl = element.attr("abs:href");
            zwListModel.type = TYPE_UPDATE;
            list.add(zwListModel);
        }

        initAdd(list);
    }


    /**
     * 获取最新入库小说
     */
    private void initAdd(List<ZWListModel> list) {

        ZWListModel pushTitle = new ZWListModel();
        pushTitle.title = TYPE_TITLE_ADD;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        ZWListModel zwListModel;
        Elements select = document.select("div.r").select("a[href]");
        for (Element element : select) {
            zwListModel = new ZWListModel();
            zwListModel.title = element.text();
            zwListModel.detailUrl = element.attr("abs:href");
            zwListModel.type = TYPE_ADD;
            list.add(zwListModel);
        }
    }

    public List<ZWListContentsModel> getZwListContents() {
        List<ZWListContentsModel> list = new ArrayList<>();
        ZWListContentsModel contentsModel;
        Elements a = document.select("#list").select("a");
        for (Element element : a) {
            contentsModel = new ZWListContentsModel();
            contentsModel.title = element.text();
            contentsModel.detailUrl = element.attr("abs:href");
            list.add(contentsModel);
        }
        return list;
    }


    public ZWListDetailModel getZwListDetail() {
        ZWListDetailModel detailModel = new ZWListDetailModel();
        Elements select = document.select("div.bottem2").select("a[href$=.html]");
        if (select.size() == 1) {
            detailModel.nextPage = select.attr("abs:href");
        } else {
            for (int i = 0; i < select.size(); i++) {
                switch (i) {
                    case 0:
                        detailModel.onPage = select.get(i).attr("abs:href");
                        break;
                    case 1:
                        detailModel.nextPage = select.get(i).attr("abs:href");
                        break;
                }
            }
        }
        detailModel.title = document.select("div.bookname").select("h1").text();
        detailModel.message = document.select("#content").html();
        return detailModel;
    }
}
