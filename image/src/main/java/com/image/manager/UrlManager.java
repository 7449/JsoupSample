package com.image.manager;

import com.framework.utils.UIUtils;
import com.image.R;


/**
 * by y on 2017/4/26
 */

public class UrlManager {

    private UrlManager() {
    }


    public static String getListUrl(String type, int tabPosition, int page) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:

                return ApiConfig.DBMZ_URL + (tabPosition + 2) + ApiConfig.dou_ban_link + page;

            case ApiConfig.Type.KK:

                return String.format(ApiConfig.KK_URL + UIUtils.INSTANCE.getStringArray(R.array.kk_array_suffix)[tabPosition], page);

            case ApiConfig.Type.M_ZI_TU:

                return ApiConfig.M_ZI_TU_URL +
                        UIUtils.INSTANCE.getStringArray(R.array.mzitu_array_suffix)[tabPosition] +
                        ApiConfig.SLASH +
                        ApiConfig.PAGE +
                        ApiConfig.SLASH + page;

            case ApiConfig.Type.MM:

                return String.format(ApiConfig.MM_URL + UIUtils.INSTANCE.getStringArray(R.array.mm_array_suffix)[tabPosition], page);

            case ApiConfig.Type.MEIZITU:

                return ApiConfig.MEIZITU_URL + String.format(UIUtils.INSTANCE.getStringArray(R.array.meizitu_array_suffix)[tabPosition], page);

            default:
                return "";
        }
    }
}
