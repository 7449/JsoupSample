package com.magnetic.manager;

/**
 * by y on 2017/6/6.
 */

public interface ApiConfig {

    String BUS = "bus";

    String MA_YI_URL = "http://www.btans.com/search/%s-first-asc-%s";

    String YING_TAO_URL = "http://www.btcherry.info/search?keyword=%s&p=%s";

    String NI_MA_URL = "https://www.nimasou.info/l/%s-first-asc-%s";

    String ZHI_ZHU_URL = "http://www.zzcili.com/so/%s-first-asc-%s";

    interface Type {
    }
}
