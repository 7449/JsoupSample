package com.fiction.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.view.ViewManager;
import com.fiction.ui.fragment.TabFragment;
import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;


/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl extends BasePresenterImpl<BaseModel, ViewManager.MainView> implements PresenterManager.MainPresenter {

    private Fragment zwFragment, kswFragment, bqgFragment;

    public static final int FIRST_FRAGMENT = -1;

    private static final int TYPE_ZW_FRAGMENT = 0;
    private static final String TYPE_ZW_TAG = "ZW";

    private static final int TYPE_KSW_FRAGMENT = 1;
    private static final String TYPE_KSW_TAG = "KSW";

    private static final int TYPE_BGQ_FRAGMENT = 2;
    private static final String TYPE_BQG_TAG = "BQG";


    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.search:
                view.switchSearch();
                break;
            case FIRST_FRAGMENT:
            case R.id.fiction_81:
                setSelectFragment(TYPE_ZW_FRAGMENT);
                break;
            case R.id.bi_qu_ge:
                setSelectFragment(TYPE_BGQ_FRAGMENT);
                break;
            case R.id.ksw:
                setSelectFragment(TYPE_KSW_FRAGMENT);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = view.getMainActivity().getSupportFragmentManager().findFragmentByTag(TYPE_ZW_TAG);
        if (fragment != null && fragment.isHidden()) {
            setSelectFragment(TYPE_ZW_FRAGMENT);
            view.selectMenuFirst();
        } else {
            view.onBack();
        }
    }

    @Override
    public void onMainDestroy() {
        if (null != zwFragment) {
            zwFragment = null;
        }
        if (null != kswFragment) {
            kswFragment = null;
        }
        if (null != bqgFragment) {
            bqgFragment = null;
        }
    }

    private void setSelectFragment(int type) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case TYPE_ZW_FRAGMENT:
                zwFragment = manager.findFragmentByTag(TYPE_ZW_TAG);
                hideFragment(transaction);
                if (null == zwFragment) {
                    zwFragment = TabFragment.newInstance(ApiConfig.Type.ZW_81);
                    transaction.add(R.id.fragment, zwFragment, TYPE_ZW_TAG);
                } else {

                    transaction.show(zwFragment);
                }
                break;
            case TYPE_KSW_FRAGMENT:
                kswFragment = manager.findFragmentByTag(TYPE_KSW_TAG);
                hideFragment(transaction);
                if (null == kswFragment) {
                    kswFragment = TabFragment.newInstance(ApiConfig.Type.KSW);
                    transaction.add(R.id.fragment, kswFragment, TYPE_KSW_TAG);
                } else {
                    transaction.show(kswFragment);
                }
                break;
            case TYPE_BGQ_FRAGMENT:
                bqgFragment = manager.findFragmentByTag(TYPE_BQG_TAG);
                hideFragment(transaction);
                if (null == bqgFragment) {
                    bqgFragment = TabFragment.newInstance(ApiConfig.Type.BI_QU_GE);
                    transaction.add(R.id.fragment, bqgFragment, TYPE_BQG_TAG);
                } else {
                    transaction.show(bqgFragment);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (null != kswFragment) {
            transaction.hide(kswFragment);
        }
        if (null != bqgFragment) {
            transaction.hide(bqgFragment);
        }
        if (null != zwFragment) {
            transaction.hide(zwFragment);
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
