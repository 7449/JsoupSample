package com.fiction.search.v;

import com.fiction.search.m.SearchModel;
import com.framework.base.BaseView;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public interface SearchView extends BaseView<List<SearchModel>> {
    void fictionNameEmpty();
}
