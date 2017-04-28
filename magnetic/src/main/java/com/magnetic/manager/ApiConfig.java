package com.magnetic.manager;

/**
 * by y on 2017/4/28
 */

public interface ApiConfig {

    String BT_CHERRY_URL = "http://www.btcherry.info/search?keyword=%s&p=%s"; //樱桃

    String BT_URLS_URL = "http://www.bturls.net/search/%s_ctime_%s.html"; //磁力链

    String BT_CILISOU_URL = "http://www.cilisou.cn/s.php?q=%s&p=%s&order="; //磁力搜

    interface Type {
        String MAGNETIC = "magnetic";
    }
}
