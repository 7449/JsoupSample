package com.image.search.list.presenter;

import android.support.annotation.NonNull;


/**
 * by y on 2017/4/19.
 */

public interface SearchListPresenter {
    void netWorkRequest(@NonNull String searchType, @NonNull String content, int page);
}
