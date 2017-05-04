package com.framework.utils


import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ArrayRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.framework.App


/**
 * by y on 2016/7/26.
 */
object UIUtils {


    val context: Context
        get() = App.instance.applicationContext

    fun getString(id: Int): String {
        return context.getString(id)
    }

    fun getStringArray(@ArrayRes id: Int): Array<String> {
        return context.resources.getStringArray(id)
    }

    fun getAdapterView(viewGroup: ViewGroup, id: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(id, viewGroup, false)
    }

    fun startActivity(clz: Class<*>) {
        val intent = Intent(context, clz)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }


    fun toast(o: Any) {
        Toast.makeText(context, o.toString(), Toast.LENGTH_SHORT).show()
    }

    fun snackBar(view: View, `object`: Any) {
        Snackbar.make(view, `object`.toString() + "", Snackbar.LENGTH_SHORT).show()
    }

    fun copy(content: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        cm.text = content
    }
}
