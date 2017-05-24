package com.framework.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * by y on 2017/3/24
 */

public abstract class BaseDialogFragment extends DialogFragment {

    protected View mRootView = null;
    protected AlertDialog mAlertDialog = null;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(getCancelable());
        return getDialog();
    }

    protected <T extends View> T getView(int id) {
        //noinspection unchecked
        return (T) mRootView.findViewById(id);
    }

    public View getRootView(int id) {
        return View.inflate(getActivity(), id, null);
    }

    public abstract AlertDialog getDialog();


    protected abstract boolean getCancelable();
}
