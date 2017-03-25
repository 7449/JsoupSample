package com.image.manager;

import com.framework.utils.UIUtils;
import com.image.R;

/**
 * by y on 2017/3/23
 */

public class CorrectUtils {


    private CorrectUtils() {
    }

    public static int getDetailTitle(String type) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI_DETAIL:
                return R.string.dbmz_title;
            default:
                return R.string.mzitu_title;
        }

    }

    public static String getDetailType(String type) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return ApiConfig.Type.DOU_BAN_MEI_ZI_DETAIL;
            case ApiConfig.Type.M_ZI_TU:
                return ApiConfig.Type.M_ZI_TU_DETAIL;
            default:
                return null;
        }
    }

    public static String getListUrl(int id, int page, String type) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return ApiConfig.DBMZ_API + (id + 2) + ApiConfig.dou_ban_link + page;
            default:
                return ApiConfig.M_ZI_TU_API + UIUtils.getStringArray(R.array.mzitu_array_suffix)[id] + ApiConfig.SLASH + ApiConfig.PAGE + ApiConfig.SLASH + page;
        }
    }

    public static String getDetailUrl(String type, String url, int page) {
        switch (type) {
            case ApiConfig.Type.DOU_BAN_MEI_ZI_DETAIL:
                return url;
            default:
                return url + ApiConfig.SLASH + page;
        }
    }
}
