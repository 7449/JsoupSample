package com.framework.widget;

import android.support.v7.app.AlertDialog;

import com.framework.R;
import com.framework.base.BaseDialogFragment;

/**
 * by y on 2017/3/24
 */

public class FtpDialogFragment extends BaseDialogFragment {

    private FtpXLListener listener;

    public static FtpDialogFragment newInstance() {
        return new FtpDialogFragment();
    }

    @Override
    public AlertDialog getDialog() {
        listener = (FtpXLListener) getActivity();
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setMessage(getString(R.string.dialog_download_tips))
                .setNegativeButton(getString(R.string.dialog_exit_cancel), null)
                .setPositiveButton(getString(R.string.dialog_exit_positive), (dialog, which) -> {
                    if (listener != null) {
                        listener.onFtpNext();
                    }
                })
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return false;
    }

    public interface FtpXLListener {
        void onFtpNext();
    }
}
