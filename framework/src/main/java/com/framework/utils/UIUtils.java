package com.framework.utils;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framework.App;


/**
 * by y on 2016/7/26.
 */
public class UIUtils {


    public static Context getContext() {
        return App.getInstance().getApplicationContext();
    }

    public static String getString(int id) {
        return getContext().getString(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static View getAdapterView(ViewGroup viewGroup, int id) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    public static void startActivity(@NonNull Class<?> clz) {
        Intent intent = new Intent(getContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void startActivity(@NonNull Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }


    public static void toast(Object o) {
        Toast.makeText(getContext(), o.toString(), Toast.LENGTH_SHORT).show();
    }

    public static void snackBar(View view, Object object) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT).show();
    }

    public static void copy(String content) {
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
    }
}
