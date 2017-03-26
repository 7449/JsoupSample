package com.image.manager;

/**
 * by y on 2016/7/26.
 */
public interface ApiConfig {
    String SLASH = "/";
    String PAGE = "page";

    String DBMZ_URL = "http://www.dbmeinv.com/dbgroup/show.htm?cid=";
    String dou_ban_link = "&pager_offset=";

    String M_ZI_TU_URL = "http://www.mzitu.com/";

    String MM_URL = "http://www.mmjpg.com/";

    interface Type {
        String DOU_BAN_MEI_ZI = "doubanmeizi";
        String M_ZI_TU = "mzitu";
        String MM = "mm";
    }
}
