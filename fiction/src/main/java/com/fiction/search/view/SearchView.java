package com.fiction.search.view;

import com.fiction.search.model.SearchModel;
import com.framework.base.BaseView;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public interface SearchView extends BaseView<List<SearchModel>> {
    void fictionNameEmpty();
}
