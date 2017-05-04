package com.framework.utils

import android.Manifest
import android.app.Activity

import com.rxjsoupnetwork.manager.RxJsoupDisposeManager
import com.tbruyelle.rxpermissions2.RxPermissions

import io.reactivex.observers.DisposableObserver

/**
 * by y on 2017/3/8
 */

object PermissionsUtils {
    val WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    fun requestPermission(callBack: PermissionsCallBack,
                          vararg permission: String): DisposableObserver<Boolean> {
        val disposableObserver = RxPermissions(callBack.permissionActivity)
                .request(*permission)
                .subscribeWith(
                        object : DisposableObserver<Boolean>() {
                            override fun onNext(aBoolean: Boolean?) {
                                if (aBoolean!!) {
                                    callBack.onPermissionsSuccess()
                                } else {
                                    callBack.onPermissionsError()
                                }
                            }

                            override fun onError(e: Throwable) {
                                callBack.onPermissionsError()
                            }

                            override fun onComplete() {

                            }
                        })
        RxJsoupDisposeManager.getInstance().addDisposable(disposableObserver)
        return disposableObserver
    }

    interface PermissionsCallBack {
        fun onPermissionsSuccess()

        fun onPermissionsError()

        val permissionActivity: Activity
    }
}
