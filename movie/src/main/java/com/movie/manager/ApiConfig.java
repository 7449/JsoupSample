package com.movie.manager;

/**
 * by y on 2016/7/26.
 */
public interface ApiConfig {

    String DYTT_URL = "http://www.dytt8.net/index2.htm";
    String DY_2018_URL = "http://www.dy2018.com/";
    String DYTT_XL = "http://www.ygdy8.net/html/gndy/index.html";
    String XIAO_PIAN_URL = "http://www.xiaopian.com/html/";
    String PIAO_HUA_URL = "http://www.piaohua.com/html/";
    String K_567_URL = "http://www.567k.info/?";

    interface Type {
        String DYTT = "dytt";
        String DY_2018 = "dy2018";
        String XIAO_PIAN = "xiaopian";
        String PIAO_HUA = "piaohua";
        String K_567 = "k567";

        int DYTT_XL_TYPE = 0;
        int DYTT_CHOSEN_TYPE = 1;
    }
}
