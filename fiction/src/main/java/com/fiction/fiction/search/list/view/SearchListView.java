package com.fiction.fiction.search.list.view;

import com.fiction.fiction.search.list.model.SearchListModel;
import com.framework.base.BaseListView;

import java.util.List;

/**
 * by y on 2017/1/8.
 */

public interface SearchListView extends BaseListView<List<SearchListModel>> {
    void fictionNameEmpty();
}
