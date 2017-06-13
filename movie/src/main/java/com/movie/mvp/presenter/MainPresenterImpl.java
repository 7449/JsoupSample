package com.movie.mvp.presenter;


import android.support.annotation.MenuRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BasePresenterImpl;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.mvp.view.ViewManager;
import com.movie.ui.fragment.TabFragment;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl extends BasePresenterImpl<BaseModel, ViewManager.MainView> implements PresenterManager.MainPresenter {
    public static final int FIRST_FRAGMENT = -1;
    private static final int TYPE_DYTT_FRAGMENT = 0;
    private static final String TYPE_DYTT_TAG = "DYTT";
    private static final int TYPE_DY2018_FRAGMENT = 1;
    private static final String TYPE_DY2018_TAG = "DY2018";
    private static final int TYPE_XP_FRAGMENT = 2;
    private static final String TYPE_XP_TAG = "XP";
    private static final int TYPE_PH_FRAGMENT = 3;
    private static final String TYPE_PH_TAG = "PH";
    private static final int TYPE_K567_FRAGMENT = 4;
    private static final String TYPE_K567_TAG = "k567";
    private Fragment dyttFragment, dy2018Fragment, xpFragment, phFragment, k567Fragment;

    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.dytt:
            case FIRST_FRAGMENT:
                setSelectFragment(TYPE_DYTT_FRAGMENT);
                break;
            case R.id.dy_2018:
                setSelectFragment(TYPE_DY2018_FRAGMENT);
                break;
            case R.id.xiaopian:
                setSelectFragment(TYPE_XP_FRAGMENT);
                break;
            case R.id.piaohua:
                setSelectFragment(TYPE_PH_FRAGMENT);
                break;
            case R.id.k567:
                setSelectFragment(TYPE_K567_FRAGMENT);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = view.getMainActivity().getSupportFragmentManager().findFragmentByTag(TYPE_DYTT_TAG);
        if (fragment != null && fragment.isHidden()) {
            setSelectFragment(TYPE_DYTT_FRAGMENT);
            view.selectMenuFirst();
        } else {
            view.onBack();
        }
    }

    @Override
    public void onMainDestroy() {
        if (null != dyttFragment) {
            dyttFragment = null;
        }
        if (null != dy2018Fragment) {
            dy2018Fragment = null;
        }
        if (null != xpFragment) {
            xpFragment = null;
        }
        if (null != phFragment) {
            phFragment = null;
        }
        if (null != k567Fragment) {
            k567Fragment = null;
        }
    }

    private void setSelectFragment(int type) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case TYPE_DYTT_FRAGMENT:
                dyttFragment = manager.findFragmentByTag(TYPE_DYTT_TAG);
                hideFragment(transaction);
                if (null == dyttFragment) {
                    dyttFragment = TabFragment.newInstance(ApiConfig.Type.DYTT);
                    transaction.add(R.id.fragment, dyttFragment, TYPE_DYTT_TAG);
                } else {
                    transaction.show(dyttFragment);
                }
                break;
            case TYPE_DY2018_FRAGMENT:
                dy2018Fragment = manager.findFragmentByTag(TYPE_DY2018_TAG);
                hideFragment(transaction);
                if (null == dy2018Fragment) {
                    dy2018Fragment = TabFragment.newInstance(ApiConfig.Type.DY_2018);
                    transaction.add(R.id.fragment, dy2018Fragment, TYPE_DY2018_TAG);
                } else {
                    transaction.show(dy2018Fragment);
                }
                break;
            case TYPE_XP_FRAGMENT:
                xpFragment = manager.findFragmentByTag(TYPE_XP_TAG);
                hideFragment(transaction);
                if (null == xpFragment) {
                    xpFragment = TabFragment.newInstance(ApiConfig.Type.XIAO_PIAN);
                    transaction.add(R.id.fragment, xpFragment, TYPE_XP_TAG);
                } else {
                    transaction.show(xpFragment);
                }
                break;
            case TYPE_PH_FRAGMENT:
                phFragment = manager.findFragmentByTag(TYPE_PH_TAG);
                hideFragment(transaction);
                if (null == phFragment) {
                    phFragment = TabFragment.newInstance(ApiConfig.Type.PIAO_HUA);
                    transaction.add(R.id.fragment, phFragment, TYPE_PH_TAG);
                } else {
                    transaction.show(phFragment);
                }
                break;
            case TYPE_K567_FRAGMENT:
                k567Fragment = manager.findFragmentByTag(TYPE_K567_TAG);
                hideFragment(transaction);
                if (null == k567Fragment) {
                    k567Fragment = TabFragment.newInstance(ApiConfig.Type.K_567);
                    transaction.add(R.id.fragment, k567Fragment, TYPE_K567_TAG);
                } else {
                    transaction.show(k567Fragment);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (null != dyttFragment) {
            transaction.hide(dyttFragment);
        }
        if (null != dy2018Fragment) {
            transaction.hide(dy2018Fragment);
        }
        if (null != xpFragment) {
            transaction.hide(xpFragment);
        }
        if (null != phFragment) {
            transaction.hide(phFragment);
        }
        if (null != k567Fragment) {
            transaction.hide(k567Fragment);
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
