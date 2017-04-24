package com.fiction.manager;

/**
 * by y on 2017/3/25.
 */

public interface ApiConfig {

    String ON_PAGE = "上一章";
    String NEXT_PAGE = "下一章";

    //81中文网
    String SEARCH_ZW81_URL = "http://zhannei.baidu.com/cse/search?q=";
    String SEARCH_ZW81_SUFFIX = "&s=3975864432584690275";
    String ZW81_URL = "http://www.81zw.com/";

    //笔趣阁
    String BI_QU_GE_URL = "http://www.biqiuge.com/";

    //零点看书
    String KSW_URL = "http://www.00ksw.net/";

    interface Type {
        String ZW_81 = "81";
        String BI_QU_GE = "笔趣阁";
        String KSW = "零点看书";
    }
}
