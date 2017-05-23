package com.image.collection.list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.framework.base.BaseDialogFragment;
import com.image.R;

/**
 * by y on 2017/3/27
 */

public class CollectionListDialog extends BaseDialogFragment {

    private CollectionListener listener;

    public static CollectionListDialog newInstance(int position, FragmentManager fragmentManager, String tag) {
        CollectionListDialog collectionDialog = (CollectionListDialog) fragmentManager.findFragmentByTag(tag);
        if (collectionDialog == null) {
            collectionDialog = new CollectionListDialog();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        collectionDialog.setArguments(bundle);
        collectionDialog.show(fragmentManager, tag);
        return collectionDialog;
    }

    @Override
    public AlertDialog getDialog() {
        listener = (CollectionListener) getParentFragment();
        int position = getArguments().getInt("position");
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setMessage(getString(R.string.dialog_collection_message))
                .setNegativeButton(getString(R.string.dialog_exit_cancel), null)
                .setPositiveButton(getString(R.string.dialog_exit_positive), (dialog, which) -> {
                    if (listener != null) {
                        listener.onCollectionDeletedNext(position);
                    }
                })
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return false;
    }

    public interface CollectionListener {
        void onCollectionDeletedNext(int position);
    }
}
