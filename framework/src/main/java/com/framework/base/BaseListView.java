package com.framework.base;

/**
 * by y on 2017/3/26.
 * <p>
 * 使用list数据时会有没有数据的情况，这个时候应该提醒用户，BaseListView 作为一个中介 链接 View 以及 BaseView
 * <p>
 * 如果是List数据 可继承 BaseListView 实现noMore();
 */

public interface BaseListView<T> extends BaseView<T> {
    void noMore();
}
