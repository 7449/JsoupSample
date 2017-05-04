package com.framework.widget

import android.support.v7.app.AlertDialog

import com.framework.R
import com.framework.base.BaseDialogFragment

/**
 * by y on 2017/3/24
 */

class FtpDialogFragment : BaseDialogFragment() {

    private var listener: FtpXLListener? = null

    override fun getDialog(): AlertDialog {
        listener = activity as FtpXLListener
        mAlertDialog = AlertDialog.Builder(activity)
                .setMessage(getString(R.string.dialog_download_tips))
                .setNegativeButton(getString(R.string.dialog_exit_cancel), null)
                .setPositiveButton(getString(R.string.dialog_exit_positive)) { _, _ ->
                    if (listener != null) {
                        listener!!.onFtpNext()
                    }
                }
                .create()
        return (mAlertDialog as AlertDialog?)!!
    }

    override val cancelable: Boolean
        get() = false


    interface FtpXLListener {
        fun onFtpNext()
    }

    companion object {

        fun newInstance(): FtpDialogFragment {
            return FtpDialogFragment()
        }
    }
}
