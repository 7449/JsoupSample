package com.fiction.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fiction.mvp.model.FictionModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupFictionListManager {

    public static final int TYPE_HEADER = 0; //首页6个大图小说
    public static final int TYPE_UPDATE = 1;   //最近更新列表
    public static final int TYPE_ADD = 2;   //其他小说
    public static final int TYPE_TITLE = 3;

    private static final String TYPE_TITLE_LIST = "最近更新列表:";
    private static final String TYPE_TITLE_ADD = "好看的:";

    private Document document;

    private JsoupFictionListManager(Document document) {
        this.document = document;
    }

    public static JsoupFictionListManager get(@NonNull Document document) {
        return new JsoupFictionListManager(document);
    }

    public List<FictionModel> getList() {
        List<FictionModel> list = new ArrayList<>();
        initHeader(list);
        return list;
    }

    /**
     * 获取首页六个小说
     */
    private void initHeader(List<FictionModel> list) {
        FictionModel kswListModel;
        Elements select = document.select("div.item");
        for (Element element : select) {
            kswListModel = new FictionModel();
            kswListModel.title = element.select("img[src]").attr("alt");
            kswListModel.url = element.select("img[src]").attr("src");
            kswListModel.detailUrl = element.select("a:has(img)").attr("abs:href");
            kswListModel.message = element.select("dd").text();
            kswListModel.type = TYPE_HEADER;
            list.add(kswListModel);
        }
        initList(list);
    }

    /**
     * 最近更新小说列表
     */
    private void initList(List<FictionModel> list) {
        FictionModel pushTitle = new FictionModel();
        pushTitle.title = TYPE_TITLE_LIST;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        FictionModel kswListModel;
        Elements select = document.select("div#newscontent").select("div.l").select("span.s2").select("a");
        for (Element element : select) {
            kswListModel = new FictionModel();
            kswListModel.title = element.text();
            kswListModel.detailUrl = element.attr("abs:href");
            kswListModel.type = TYPE_UPDATE;
            list.add(kswListModel);
        }

        initAdd(list);
    }


    /**
     * 获取最新入库小说
     */
    private void initAdd(List<FictionModel> list) {

        FictionModel pushTitle = new FictionModel();
        pushTitle.title = TYPE_TITLE_ADD;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        FictionModel kswListModel;
        Elements select = document.select("div.r").select("a[href]");
        for (Element element : select) {
            kswListModel = new FictionModel();
            kswListModel.title = element.text();
            kswListModel.detailUrl = element.attr("abs:href");
            kswListModel.type = TYPE_ADD;
            list.add(kswListModel);
        }
    }

    public List<FictionModel> getContents() {
        List<FictionModel> list = new ArrayList<>();
        FictionModel contentsModel;
        Elements a = document.select("#list").select("a");
        for (Element element : a) {
            contentsModel = new FictionModel();
            contentsModel.title = element.text();
            contentsModel.detailUrl = element.attr("abs:href");
            list.add(contentsModel);
        }
        return list;
    }


    public FictionModel getDetail(String type) {
        FictionModel detailModel = new FictionModel();
        if (TextUtils.equals(type, ApiConfig.Type.PIAO_TIAN)) {
            Elements select = document.select("div.toplink").select("a");
            String pageUrl = select.eq(0).attr("href");
            String nextUrl = select.eq(2).attr("href");
            if (!TextUtils.equals(pageUrl, "index.html")) {
                detailModel.onPage = select.eq(0).attr("abs:href");
            }
            if (!TextUtils.equals(nextUrl, "index.html")) {
                detailModel.nextPage = select.eq(2).attr("abs:href");
            }
            document.select("div").remove();
            detailModel.title = document.select("H1").text();
            document.select("H1").remove();
            detailModel.message = document.html();
            return detailModel;
        }


        String divClass;
        switch (type) {
            case ApiConfig.Type.BI_QU_GE:
                divClass = "div.bottem";
                break;
            default:
                divClass = "div.bottem2";
                break;
        }
        Elements select = document.select(divClass).select("a[href$=.html]");
        if (select.size() == 1) {
            if (TextUtils.equals(select.text(), ApiConfig.NEXT_PAGE)) {
                detailModel.nextPage = select.attr("abs:href");
            } else {
                detailModel.onPage = select.attr("abs:href");
            }
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
