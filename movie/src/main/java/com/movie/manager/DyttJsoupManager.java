package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.movie.dytt.detail.model.DyttVideoDetailModel;
import com.movie.movie.dytt.list.model.DyttChosenModel;
import com.movie.movie.dytt.list.model.DyttNewModel;
import com.movie.movie.dytt.more.model.DyttVideoMoreModel;
import com.movie.movie.dytt.more.model.DyttXLMoreModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttJsoupManager {

    public static final int TYPE_2016 = 0;//新片精品
    public static final int TYPE_XL = 1;//迅雷电影资源
    public static final int TYPE_ZH = 2;//华语电视剧
    public static final int TYPE_US = 3;//欧美电视剧
    public static final int TYPE_TV = 4;//迅雷综艺节目
    public static final int TYPE_ACG = 5;//最新动漫资源
    private static final String CHOSEN_SUFFIX = "更多>>";


    private Document document;

    private DyttJsoupManager(Document document) {
        this.document = document;
    }

    public static DyttJsoupManager get(@NonNull Document document) {
        return new DyttJsoupManager(document);
    }


    /**
     * 获取dytt侧边栏的最新发布
     */
    public List<DyttNewModel> getDyttNewList() {
        List<DyttNewModel> dyttNewModels = new ArrayList<>();
        DyttNewModel newModel;
        Elements dyttNewElements = document.select("div.co_content2").select("a");
        for (Element element : dyttNewElements) {
            newModel = new DyttNewModel();
            newModel.setTitle(element.text());
            newModel.setDetailUrl(element.attr("abs:href"));
            dyttNewModels.add(newModel);
        }
        return dyttNewModels;
    }

    /**
     * 获取dytt中间的资源分布
     */
    public List<DyttChosenModel> getDyttChosenList() {
        List<DyttChosenModel> dyttChosenModels = new ArrayList<>();
        DyttChosenModel model;
        Elements select = document.select("div.title_all:has(a)");
        Elements itemSelect = document.select("table[cellpadding=0]");
        int size = select.size();
        for (int i = 0; i < size - 1; i++) {
            model = new DyttChosenModel();
            model.type = 0;
            model.itemType = i;
            model.setTitle(select.get(i).text().replace(CHOSEN_SUFFIX, ""));
            model.setUrl(select.get(i).select("a").attr("abs:href"));
            dyttChosenModels.add(model);
            getDyttChosenItemList(i, dyttChosenModels, itemSelect.get(i).select("a[href]"));
        }
        return dyttChosenModels;
    }

    private void getDyttChosenItemList(int itemType, List<DyttChosenModel> dyttChosenModels, Elements itemElements) {
        DyttChosenModel itemModel;
        int size = itemElements.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 != 0) {
                itemModel = new DyttChosenModel();
                itemModel.type = 1;
                itemModel.itemType = itemType;
                itemModel.setTitle(itemElements.get(i).text());
                itemModel.setUrl(itemElements.get(i).attr("abs:href"));
                dyttChosenModels.add(itemModel);
            }
        }
    }


    /**
     * 获取dytt详情
     */
    public DyttVideoDetailModel getDyttVideoDetail() {
        DyttVideoDetailModel model = new DyttVideoDetailModel();
        model.setTitle(document.select("div.title_all").eq(4).text());
        Elements select = document.select("div#Zoom");
        model.setMessage(select.html());
        return model;
    }

    /**
     * 最新电影 华语电视剧 欧美电视剧 迅雷综艺节目 最新动漫   更多
     */
    public List<DyttVideoMoreModel> getDyttMoreVideoList() {
        List<DyttVideoMoreModel> moreModels = new ArrayList<>();
        DyttVideoMoreModel model;
        Elements select = document.select("a.ulink");
        for (Element element : select) {
            if (!element.text().startsWith("[")) {
                model = new DyttVideoMoreModel();
                model.setTitle(element.text());
                model.setUrl(element.attr("abs:href"));
                moreModels.add(model);
            }
        }
        return moreModels;
    }

    /**
     * 迅雷资源更多
     */
    public List<DyttXLMoreModel> getDyttMoreXLList() {
        List<DyttXLMoreModel> dyttXLMoreModels = new ArrayList<>();
        DyttXLMoreModel model;
        Elements select = document.select("div.title_all:has(a)");
        Elements itemSelect = document.select("table[cellpadding=0]");
        int size = select.size();
        for (int i = 0; i < size; i++) {
            model = new DyttXLMoreModel();
            model.type = 0;
            model.itemType = i;
            model.setTitle(select.get(i).text().replace(CHOSEN_SUFFIX, ""));
            model.setUrl(select.get(i).select("a").attr("abs:href"));
            dyttXLMoreModels.add(model);
            getDyttMoreXLItemList(i, dyttXLMoreModels, itemSelect.get(i).select("a[href]"));
        }
        return dyttXLMoreModels;
    }

    private void getDyttMoreXLItemList(int itemType, List<DyttXLMoreModel> dyttChosenModels, Elements itemElements) {
        DyttXLMoreModel itemModel;
        int size = itemElements.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 != 0) {
                itemModel = new DyttXLMoreModel();
                itemModel.type = 1;
                itemModel.itemType = itemType;
                itemModel.setTitle(itemElements.get(i).text());
                itemModel.setUrl(itemElements.get(i).attr("abs:href"));
                dyttChosenModels.add(itemModel);
            }
        }
    }
}
