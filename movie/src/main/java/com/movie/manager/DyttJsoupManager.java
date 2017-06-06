package com.movie.manager;

import android.support.annotation.NonNull;

import com.movie.mvp.model.MovieModel;

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
    public List<MovieModel> getDyttNewList() {
        List<MovieModel> dyttNewModels = new ArrayList<>();
        MovieModel newModel;
        Elements dyttNewElements = document.select("div.co_content2").select("a");
        for (Element element : dyttNewElements) {
            newModel = new MovieModel();
            newModel.title = element.text();
            newModel.detailUrl = element.attr("abs:href");
            dyttNewModels.add(newModel);
        }
        return dyttNewModels;
    }

    /**
     * 获取dytt中间的资源分布
     */
    public List<MovieModel> getDyttChosenList() {
        List<MovieModel> dyttChosenModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("div.title_all:has(a)");
        Elements itemSelect = document.select("table[cellpadding=0]");
        int size = select.size();
        for (int i = 0; i < size - 1; i++) {
            model = new MovieModel();
            model.type = 0;
            model.itemType = i;
            model.title = select.get(i).text().replace(CHOSEN_SUFFIX, "");
            model.url = select.get(i).select("a").attr("abs:href");
            dyttChosenModels.add(model);
            getDyttChosenItemList(i, dyttChosenModels, itemSelect.get(i).select("a[href]"));
        }
        return dyttChosenModels;
    }

    private void getDyttChosenItemList(int itemType, List<MovieModel> dyttChosenModels, Elements itemElements) {
        MovieModel itemModel;
        int size = itemElements.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 != 0) {
                itemModel = new MovieModel();
                itemModel.type = 1;
                itemModel.itemType = itemType;
                itemModel.title = itemElements.get(i).text();
                itemModel.url = itemElements.get(i).attr("abs:href");
                dyttChosenModels.add(itemModel);
            }
        }
    }


    /**
     * 最新电影 华语电视剧 欧美电视剧 迅雷综艺节目 最新动漫   更多
     */
    public List<MovieModel> getDyttMoreVideoList() {
        List<MovieModel> moreModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("a.ulink");
        for (Element element : select) {
            if (!element.text().startsWith("[")) {
                model = new MovieModel();
                model.title = element.text();
                model.url = element.attr("abs:href");
                moreModels.add(model);
            }
        }
        return moreModels;
    }

    /**
     * 迅雷资源更多
     */
    public List<MovieModel> getDyttMoreXLList() {
        List<MovieModel> dyttXLMoreModels = new ArrayList<>();
        MovieModel model;
        Elements select = document.select("div.title_all:has(a)");
        Elements itemSelect = document.select("table[cellpadding=0]");
        int size = select.size();
        for (int i = 0; i < size; i++) {
            model = new MovieModel();
            model.type = 0;
            model.itemType = i;
            model.title = select.get(i).text().replace(CHOSEN_SUFFIX, "");
            model.url = select.get(i).select("a").attr("abs:href");
            dyttXLMoreModels.add(model);
            getDyttMoreXLItemList(i, dyttXLMoreModels, itemSelect.get(i).select("a[href]"));
        }
        return dyttXLMoreModels;
    }

    private void getDyttMoreXLItemList(int itemType, List<MovieModel> dyttChosenModels, Elements itemElements) {
        MovieModel itemModel;
        int size = itemElements.size();
        for (int i = 0; i < size; i++) {
            if (i % 2 != 0) {
                itemModel = new MovieModel();
                itemModel.type = 1;
                itemModel.itemType = itemType;
                itemModel.title = itemElements.get(i).text();
                itemModel.url = itemElements.get(i).attr("abs:href");
                dyttChosenModels.add(itemModel);
            }
        }
    }
}
