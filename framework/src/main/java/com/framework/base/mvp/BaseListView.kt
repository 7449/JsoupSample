package com.framework.base.mvp

/**
 * by y on 2017/3/26.
 *
 *
 * 使用list数据时会有没有数据的情况，这个时候应该提醒用户，BaseListView 作为一个中介 链接 View 以及 [BaseView]
 *
 *
 * 如果是List数据 可继承 BaseListView 实现noMore();
 */

interface BaseListView<in T> : BaseView<T> {
    fun noMore()
}
