package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.zw81.contents.model.ZWHomeContentsModel;
import com.fiction.fiction.zw81.detail.model.ZWHomeDetailModel;
import com.fiction.fiction.zw81.list.model.ZWHomeModel;

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
    public static final int TYPE_TITLE = 5;

    private static final String TYPE_TITLE_PUSH = "上期强推:";
    public static final String TYPE_TITLE_XUAN_HUAN = "玄幻:";
    public static final String TYPE_TITLE_XIU_ZHEN = "修真:";
    public static final String TYPE_TITLE_DU_SHI = "都市:";
    public static final String TYPE_TITLE_LI_SHI = "历史:";
    public static final String TYPE_TITLE_WANG_YOU = "网游:";
    public static final String TYPE_TITLE_KE_HUAN = "科幻:";
    private static final String TYPE_TITLE_RETCENT = "最近更新:";
    private static final String TYPE_TITLE_ADD = "最新入库:";

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

        ZWHomeModel pushTitle = new ZWHomeModel();
        pushTitle.title = TYPE_TITLE_PUSH;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

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
        int size = select.size();
        for (int i = 0; i < size; i++) {

            initTitle(list, i);


            zwHomeModel = new ZWHomeModel();
            Elements topSelect = select.get(i).select("div.top");
            zwHomeModel.url = topSelect.select("img[src]").attr("src");
            zwHomeModel.title = topSelect.select("img[src]").attr("alt");
            zwHomeModel.detailUrl = topSelect.select("a:has(img)").attr("abs:href");
            zwHomeModel.message = topSelect.select("dd").text();
            zwHomeModel.type = TYPE_HEADER;
            list.add(zwHomeModel);

            initCenter(list, select.get(i).select("li:has(a)"));
        }

        initRecent(list);
    }

    /**
     * 添加6个小说区的title
     */
    private void initTitle(List<ZWHomeModel> list, int i) {
        ZWHomeModel pushTitle = new ZWHomeModel();
        pushTitle.type = TYPE_TITLE;
        switch (i) {
            case 0:
                pushTitle.title = TYPE_TITLE_XUAN_HUAN;
                break;
            case 1:
                pushTitle.title = TYPE_TITLE_XIU_ZHEN;
                break;
            case 2:
                pushTitle.title = TYPE_TITLE_DU_SHI;
                break;
            case 3:
                pushTitle.title = TYPE_TITLE_LI_SHI;
                break;
            case 4:
                pushTitle.title = TYPE_TITLE_WANG_YOU;
                break;
            case 5:
                pushTitle.title = TYPE_TITLE_KE_HUAN;
                break;
        }
        list.add(pushTitle);
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
    }

    /**
     * 最近更新小说列表
     */
    private void initRecent(List<ZWHomeModel> list) {

        ZWHomeModel pushTitle = new ZWHomeModel();
        pushTitle.title = TYPE_TITLE_RETCENT;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        ZWHomeModel zwHomeModel;
        Elements select = document.select("div#newscontent").select("div.l").select("span.s2").select("a");
        for (Element element : select) {
            zwHomeModel = new ZWHomeModel();
            zwHomeModel.title = element.text();
            zwHomeModel.detailUrl = element.attr("abs:href");
            zwHomeModel.type = TYPE_RECENT;
            list.add(zwHomeModel);
        }

        initAdd(list);
    }

    /**
     * 获取最新入库小说
     */
    private void initAdd(List<ZWHomeModel> list) {

        ZWHomeModel pushTitle = new ZWHomeModel();
        pushTitle.title = TYPE_TITLE_ADD;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

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

    public List<ZWHomeContentsModel> getZwHomeContents() {
        List<ZWHomeContentsModel> list = new ArrayList<>();
        ZWHomeContentsModel contentsModel;
        Elements a = document.select("#list").select("a");
        for (Element element : a) {
            contentsModel = new ZWHomeContentsModel();
            contentsModel.title = element.text();
            contentsModel.detailUrl = element.attr("abs:href");
            list.add(contentsModel);
        }
        return list;
    }


    public ZWHomeDetailModel getZwHomeDetail() {
        ZWHomeDetailModel detailModel = new ZWHomeDetailModel();
        Elements select = document.select("div.bottem2").select("a[href$=.html]");
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
        detailModel.title = document.select("div.bookname").select("h1").text();
        detailModel.message = document.select("#content").html();
        return detailModel;
    }
}
