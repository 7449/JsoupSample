package com.framework.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View

/**
 * by y on 2017/3/24
 */

abstract class BaseDialogFragment : DialogFragment() {

    protected var mRootView: View? = null
    protected var mAlertDialog: AlertDialog? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = cancelable
        return dialog
    }

    protected fun <T : View> getView(id: Int): T {
        return mRootView!!.findViewById(id) as T
    }

    fun getRootView(id: Int): View {
        return View.inflate(activity, id, null)
    }

    abstract override fun getDialog(): AlertDialog


    protected abstract val cancelable: Boolean
}
