package com.magnetic.magnetic.list.widget;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.framework.base.BaseDialogFragment;
import com.magnetic.R;

/**
 * by y on 2017/4/28
 */

public class MagneticSearchDialogFragment extends BaseDialogFragment {

    private MagneticListener magneticListener;

    public static MagneticSearchDialogFragment newInstance() {
        return new MagneticSearchDialogFragment();
    }

    @Override
    public AlertDialog getDialog() {
        magneticListener = (MagneticListener) getParentFragment();
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setMessage(getString(R.string.dialog_download_tips))
                .setNegativeButton(getString(R.string.dialog_exit_cancel), (dialog, which) -> {
                    if (magneticListener != null) {
                        magneticListener.onSearchCancel();
                    }
                })
                .setPositiveButton(getString(R.string.dialog_exit_positive), (dialog, which) -> {
                    if (magneticListener != null) {
                        magneticListener.onSearchNext("");
                    }
                })
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return false;
    }

    public interface MagneticListener {
        void onSearchNext(@NonNull String content);

        void onSearchCancel();
    }
}
