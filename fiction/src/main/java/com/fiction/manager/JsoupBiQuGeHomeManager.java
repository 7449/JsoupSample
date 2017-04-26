package com.fiction.manager;

import android.support.annotation.NonNull;

import com.fiction.fiction.biquge.list.model.BiQuGeHomeModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class JsoupBiQuGeHomeManager {

    public static final int TYPE_HEADER = 0; //首页四个大图小说
    public static final int TYPE_HOT = 1;   //热门小说
    public static final int TYPE_CENTER = 2;   //中间6个小说区
    public static final int TYPE_RECENT = 3;   //最近更新小说列表
    public static final int TYPE_ADD = 4;   //最新入库小说
    public static final int TYPE_TITLE = 5;

    private static final String TYPE_TITLE_HOT = "热门小说:";
    private static final String TYPE_TITLE_RETCENT = "最近更新:";
    private static final String TYPE_TITLE_ADD = "最新入库:";

    public static final String TYPE_TITLE_XUAN_HUAN = "玄幻:";
    public static final String TYPE_TITLE_XIU_ZHEN = "修真:";
    public static final String TYPE_TITLE_DU_SHI = "都市:";
    public static final String TYPE_TITLE_CHUAN_YUE = "穿越:";
    public static final String TYPE_TITLE_WANG_YOU = "网游:";
    public static final String TYPE_TITLE_KE_HUAN = "科幻:";

    private Document document;

    private JsoupBiQuGeHomeManager(Document document) {
        this.document = document;
    }

    public static JsoupBiQuGeHomeManager get(@NonNull Document document) {
        return new JsoupBiQuGeHomeManager(document);
    }

    public List<BiQuGeHomeModel> getbiqugeHome() {
        List<BiQuGeHomeModel> list = new ArrayList<>();
        initHeader(list);
        return list;
    }

    /**
     * 获取首页四个小说
     */
    private void initHeader(List<BiQuGeHomeModel> list) {
        BiQuGeHomeModel biqugeModel;
        Elements select = document.select("div.item");
        for (Element element : select) {
            biqugeModel = new BiQuGeHomeModel();
            biqugeModel.title = element.select("img[src]").attr("alt");
            biqugeModel.url = element.select("img[src]").attr("src");
            biqugeModel.detailUrl = element.select("a:has(img)").attr("abs:href");
            biqugeModel.message = element.select("dd").text();
            biqugeModel.type = TYPE_HEADER;
            list.add(biqugeModel);
        }

        initPush(list);
    }


    /**
     * 获取热门小说
     */
    private void initPush(List<BiQuGeHomeModel> list) {
        BiQuGeHomeModel biqugeModel;
        Elements select = document.select("div.r").eq(0).select("a[href]");

        BiQuGeHomeModel hotTitle = new BiQuGeHomeModel();
        hotTitle.title = TYPE_TITLE_HOT;
        hotTitle.type = TYPE_TITLE;
        list.add(hotTitle);

        for (Element element : select) {
            biqugeModel = new BiQuGeHomeModel();
            biqugeModel.title = element.text();
            biqugeModel.detailUrl = element.attr("abs:href");
            biqugeModel.type = TYPE_HOT;
            list.add(biqugeModel);
        }

        initCenterHeader(list);
    }

    /**
     * 获取中间6个小说区头部
     */
    private void initCenterHeader(List<BiQuGeHomeModel> list) {
        BiQuGeHomeModel biqugeHomeModel;
        Elements select = document.select("div.content");
        int size = select.size();
        for (int i = 0; i < size; i++) {

            initTitle(list, i);


            biqugeHomeModel = new BiQuGeHomeModel();
            Elements topSelect = select.get(i).select("div.top");
            biqugeHomeModel.url = topSelect.select("img[src]").attr("src");
            biqugeHomeModel.title = topSelect.select("img[src]").attr("alt");
            biqugeHomeModel.detailUrl = topSelect.select("a:has(img)").attr("abs:href");
            biqugeHomeModel.message = topSelect.select("dd").text();
            biqugeHomeModel.type = TYPE_HEADER;
            list.add(biqugeHomeModel);

            initCenter(list, select.get(i).select("li:has(a)"));
        }

        initRecent(list);
    }

    /**
     * 添加6个小说区的title
     */
    private void initTitle(List<BiQuGeHomeModel> list, int i) {
        BiQuGeHomeModel pushTitle = new BiQuGeHomeModel();
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
                pushTitle.title = TYPE_TITLE_CHUAN_YUE;
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
    private void initCenter(List<BiQuGeHomeModel> list, Elements elements) {
        BiQuGeHomeModel biqugeHomeModel;
        Elements a = elements.select("a");
        for (Element element : a) {
            biqugeHomeModel = new BiQuGeHomeModel();
            biqugeHomeModel.title = element.text();
            biqugeHomeModel.detailUrl = element.attr("abs:href");
            biqugeHomeModel.type = TYPE_CENTER;
            list.add(biqugeHomeModel);
        }
    }

    /**
     * 最近更新小说列表
     */
    private void initRecent(List<BiQuGeHomeModel> list) {

        BiQuGeHomeModel pushTitle = new BiQuGeHomeModel();
        pushTitle.title = TYPE_TITLE_RETCENT;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        BiQuGeHomeModel biqugeHomeModel;
        Elements select = document.select("div#newscontent").select("div.l").select("span.s2").select("a");
        for (Element element : select) {
            biqugeHomeModel = new BiQuGeHomeModel();
            biqugeHomeModel.title = element.text();
            biqugeHomeModel.detailUrl = element.attr("abs:href");
            biqugeHomeModel.type = TYPE_RECENT;
            list.add(biqugeHomeModel);
        }

        initAdd(list);
    }

    /**
     * 获取最新入库小说
     */
    private void initAdd(List<BiQuGeHomeModel> list) {

        BiQuGeHomeModel pushTitle = new BiQuGeHomeModel();
        pushTitle.title = TYPE_TITLE_ADD;
        pushTitle.type = TYPE_TITLE;
        list.add(pushTitle);

        BiQuGeHomeModel biqugeHomeModel;
        Elements select = document.select("div.r").eq(1).select("a[href]");
        for (Element element : select) {
            biqugeHomeModel = new BiQuGeHomeModel();
            biqugeHomeModel.title = element.text();
            biqugeHomeModel.detailUrl = element.attr("abs:href");
            biqugeHomeModel.type = TYPE_ADD;
            list.add(biqugeHomeModel);
        }
    }

}
