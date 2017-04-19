package com.search;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * by y on 2017/4/19
 */

public class Utils {

    public static void offKeyboard(EditText editText) {
        if (detectKeyboard(editText)) {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void openKeyboard(EditText editText) {
        if (!detectKeyboard(editText)) {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private static boolean detectKeyboard(EditText editText) {
        //true 弹出状态
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void startAnimator(View view, Animation.AnimationListener animationListener) {
        TranslateAnimation mStartAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mStartAnimation.setDuration(400);
        mStartAnimation.setAnimationListener(animationListener);
        view.startAnimation(mStartAnimation);
    }

    public static void endAnimator(View view, Animation.AnimationListener animationListener) {
        TranslateAnimation mEndAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mEndAnimation.setDuration(400);
        mEndAnimation.setAnimationListener(animationListener);
        view.startAnimation(mEndAnimation);
    }

    public static class SimpleAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
