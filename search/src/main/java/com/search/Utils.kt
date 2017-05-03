package com.search

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * by y on 2017/4/19
 */

internal object Utils {

    fun offKeyboard(editText: EditText) {
        if (detectKeyboard(editText)) {
            val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }

    fun openKeyboard(editText: EditText) {
        if (!detectKeyboard(editText)) {
            val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun detectKeyboard(editText: EditText): Boolean {
        //true 弹出状态
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    fun startAnimator(view: View, animationListener: Animation.AnimationListener) {
        val mStartAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f)
        mStartAnimation.duration = 400
        mStartAnimation.setAnimationListener(animationListener)
        view.startAnimation(mStartAnimation)
    }

    fun endAnimator(view: View, animationListener: Animation.AnimationListener) {
        val mEndAnimation = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f)
        mEndAnimation.duration = 400
        mEndAnimation.setAnimationListener(animationListener)
        view.startAnimation(mEndAnimation)
    }

    internal open class SimpleAnimationListener : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {

        }

        override fun onAnimationEnd(animation: Animation) {

        }

        override fun onAnimationRepeat(animation: Animation) {

        }
    }

}
