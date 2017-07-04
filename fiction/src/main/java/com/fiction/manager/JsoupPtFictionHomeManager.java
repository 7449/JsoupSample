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
 * by y on 03/07/2017.
 */

public class JsoupPtFictionHomeManager {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_TITLE_MORE = 1;
    public static final int TYPE_HEADER = 2;
    public static final int TYPE_HOT = 3;
    public static final int TYPE_ITEM = 4;
    private static final String TYPE_TITLE_FT = "新书推荐";
    private static final String TYPE_TITLE_HOT = "火爆连载";
    private static final String TYPE_TITLE_NEW = "最近更新";
    private static final String TYPE_TITLE_ALL = "全本小说推荐";
    private static final String TYPE_TITLE_TOTAL = "总推荐榜";
    private static final String TYPE_TITLE_MONTH = "本月推荐";
    private static final String TYPE_TITLE_ESSAY_TOTAL = "文章总排行";
    private static final String TYPE_TITLE_ESSAY_MONTH = "文章月排行";
    private static final String TYPE_TITLE_ESSAY_DAY = "文章日排行";
    private static final String TYPE_TITLE_COLLECTION = "收藏排行";
    private static final String TYPE_TITLE_VIP = "最新VIP小说";
    private Document document;

    private JsoupPtFictionHomeManager(Document document) {
        this.document = document;
    }

    public static JsoupPtFictionHomeManager get(@NonNull Document document) {
        return new JsoupPtFictionHomeManager(document);
    }

    public List<FictionModel> getHomeList() {
        List<FictionModel> list = new ArrayList<>();
        list.add(getTitle(TYPE_TITLE_FT));
        FictionModel fictionModel;
        Elements select = document.select("div.blockcontent").eq(4).select("div#fengtui");
        for (Element element : select) {
            fictionModel = new FictionModel();
            fictionModel.url = element.select("div#fengtuipic").select("img").attr("src");
            fictionModel.title = element.select("div#fengtuiword").select("a").attr("title");
            fictionModel.detailUrl = element.select("div#fengtuiword").select("a").attr("abs:href");
            fictionModel.message = element.select("div#fengtuiword").select("a").text();
            fictionModel.type = TYPE_HEADER;
            list.add(fictionModel);
        }
        initHot(list);
        return list;
    }

    private void initHot(List<FictionModel> list) {
        list.add(getTitle(TYPE_TITLE_HOT));
        FictionModel fictionModel;
        Elements select = document.select("div.blockcontent").eq(7).select("td");
        for (Element element : select) {
            fictionModel = new FictionModel();
            fictionModel.url = element.select("img").attr("src");
            fictionModel.title = element.select("a").eq(1).text();
            fictionModel.detailUrl = element.select("a").eq(1).attr("abs:href");
            fictionModel.type = TYPE_HOT;
            list.add(fictionModel);
        }
        initNew(list);
    }

    private void initNew(List<FictionModel> list) {
        list.add(getTitle(TYPE_TITLE_NEW));
        FictionModel fictionModel;
        Elements select = document.select("div.blockcontent").eq(8).select("li");
        for (Element element : select) {
            String title = element.select("a").eq(0).text();
            String detailUrl = element.select("a").eq(0).attr("abs:href");
            if (!TextUtils.isEmpty(detailUrl)) {
                fictionModel = new FictionModel();
                fictionModel.title = title;
                fictionModel.detailUrl = detailUrl;
                fictionModel.type = TYPE_ITEM;
                list.add(fictionModel);
            }
        }

        done(list);
    }

    private void done(List<FictionModel> list) {
        for (int j = 0; j < 8; j++) {
            switch (j) {
                case 0:
                    setData(list, 0, TYPE_TITLE_ALL);
                    break;
                case 1:
                    setData(list, 1, TYPE_TITLE_ESSAY_TOTAL);
                    break;
                case 2:
                    setData(list, 2, TYPE_TITLE_ESSAY_MONTH);
                    break;
                case 3:
                    setData(list, 3, TYPE_TITLE_VIP);
                    break;
                case 4:
                    setData(list, 10, TYPE_TITLE_TOTAL);
                    break;
                case 5:
                    setData(list, 11, TYPE_TITLE_MONTH);
                    break;
                case 6:
                    setData(list, 12, TYPE_TITLE_COLLECTION);
                    break;
                case 7:
                    setData(list, 13, TYPE_TITLE_ESSAY_DAY);
                    break;
            }
        }
    }

    private void setData(List<FictionModel> list, int eq, String type) {
        Elements select = document.select("div.blockcontent").eq(eq);
        Elements itemElements = select.select("li");
        list.add(getMoreTitle(type, select));
        FictionModel fictionModel;
        for (Element element : itemElements) {
            fictionModel = new FictionModel();
            Elements a = element.select("a");
            fictionModel.title = a.text();
            fictionModel.detailUrl = a.attr("abs:href");
            fictionModel.type = TYPE_ITEM;
            list.add(fictionModel);
        }
    }

    private FictionModel getMoreTitle(String type, Elements elements) {
        FictionModel fictionModel = new FictionModel();
        fictionModel.title = type;
        fictionModel.type = TYPE_TITLE_MORE;
        fictionModel.detailUrl = elements.select("div.more").select("a[href]").attr("abs:href");
        return fictionModel;
    }

    private FictionModel getTitle(String type) {
        FictionModel fictionModel = new FictionModel();
        fictionModel.title = type;
        fictionModel.type = TYPE_TITLE;
        return fictionModel;
    }
}
