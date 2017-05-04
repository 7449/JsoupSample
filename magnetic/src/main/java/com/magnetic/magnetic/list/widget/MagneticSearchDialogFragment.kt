package com.magnetic.magnetic.list.widget

import android.support.v7.app.AlertDialog

import com.framework.base.BaseDialogFragment
import com.magnetic.R

/**
 * by y on 2017/4/28
 */

class MagneticSearchDialogFragment : BaseDialogFragment() {

    private var magneticListener: MagneticListener? = null

    override fun getDialog(): AlertDialog {
        magneticListener = parentFragment as MagneticListener
        mAlertDialog = AlertDialog.Builder(activity)
                .setMessage(getString(R.string.dialog_download_tips))
                .setNegativeButton(getString(R.string.dialog_exit_cancel)) { dialog, which ->
                    if (magneticListener != null) {
                        magneticListener!!.onSearchCancel()
                    }
                }
                .setPositiveButton(getString(R.string.dialog_exit_positive)) { dialog, which ->
                    if (magneticListener != null) {
                        magneticListener!!.onSearchNext("")
                    }
                }
                .create()
        return mAlertDialog!!
    }

    override val cancelable: Boolean
        get() = false

    interface MagneticListener {
        fun onSearchNext(content: String)

        fun onSearchCancel()
    }

    companion object {

        fun newInstance(): MagneticSearchDialogFragment {
            return MagneticSearchDialogFragment()
        }
    }
}
