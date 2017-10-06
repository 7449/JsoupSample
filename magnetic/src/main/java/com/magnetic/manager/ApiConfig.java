package com.magnetic.manager;

/**
 * by y on 2017/6/6.
 */

public interface ApiConfig {

    String BUS = "bus";

    String MA_YI_URL = "http://www.btanv.com/search/%s-first-asc-%s";

    String YING_TAO_URL = "http://www.btcerise.net/search?keyword=%s&p=%s";

    String NI_MA_URL = "http://www.nimasou.co/l/%s-first-asc-%s";

    String ZHI_ZHU_URL = "http://www.zzcili.org/so/%s-first-asc-%s";

    String D_S_URL = "http://www.diaosisou.org/list/%s/%s/time_d";

    String CILILIAN_URL = "http://cililiana.com//list/%s/%s/time.html";

    interface Type {
    }
}
