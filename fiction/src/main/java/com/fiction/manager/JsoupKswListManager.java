package com.fiction.manager;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fiction.fiction.contents.model.FictionContentsModel;
import com.fiction.fiction.detail.model.FictionDetailModel;
import com.fiction.fiction.ksw.list.model.KswListModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupKswListManager {

    public static final int TYPE_HEADER = 0; //首页6个大图小说
    public static final int TYPE_UPDATE = 1;   //最近更新列表
    public static final int TYPE_ADD = 2;   //其他小说
    public static final int TYPE_TITLE = 3;

    private static final String TYPE_TITLE_LIST = "最近更新列表:";
    private static final String TYPE_TITLE_ADD = "好看的:";

    private Document document;

    private JsoupKswListManager(Document document) {
        this.document = document;
    }

    public static JsoupKswListManager get(@NonNull Document document) {
        return new JsoupKswListManager(document);
    }

    public List<KswListModel> getKswList() {
        List<KswListModel> list = new ArrayList<>();
        initHeader(list);
        return list;
    }

    /**
     * 获取首页六个小说
     */
    private void initHeader(List<KswListModel> list) {
        KswListModel kswListModel;
        Elements select = document.select("div.item");
        for (Element element : select) {
            kswListModel = new KswListModel();
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
    private void initList(List<KswListModel> list) {
        KswListModel pushTitle = new KswListModel();
        pushTitle.title = TYPE_TITLE_LIST;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        KswListModel kswListModel;
        Elements select = document.select("div#newscontent").select("div.l").select("span.s2").select("a");
        for (Element element : select) {
            kswListModel = new KswListModel();
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
    private void initAdd(List<KswListModel> list) {

        KswListModel pushTitle = new KswListModel();
        pushTitle.title = TYPE_TITLE_ADD;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        KswListModel kswListModel;
        Elements select = document.select("div.r").select("a[href]");
        for (Element element : select) {
            kswListModel = new KswListModel();
            kswListModel.title = element.text();
            kswListModel.detailUrl = element.attr("abs:href");
            kswListModel.type = TYPE_ADD;
            list.add(kswListModel);
        }
    }

    public List<FictionContentsModel> getKswListContents() {
        List<FictionContentsModel> list = new ArrayList<>();
        FictionContentsModel contentsModel;
        Elements a = document.select("#list").select("a");
        for (Element element : a) {
            contentsModel = new FictionContentsModel();
            contentsModel.title = element.text();
            contentsModel.detailUrl = element.attr("abs:href");
            list.add(contentsModel);
        }
        return list;
    }


    public FictionDetailModel getKswListDetail() {
        FictionDetailModel detailModel = new FictionDetailModel();
        Elements select = document.select("div.bottem2").select("a[href$=.html]");
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
