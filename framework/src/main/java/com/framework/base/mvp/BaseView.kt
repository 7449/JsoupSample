package com.framework.base.mvp

/**
 * by y on 2016/10/30.
 */

interface BaseView<in T> {

    fun netWorkSuccess(data: T)

    fun netWorkError()

    fun showProgress()

    fun hideProgress()
}
