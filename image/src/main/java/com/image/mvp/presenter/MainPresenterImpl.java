package com.image.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BasePresenterImpl;
import com.image.R;
import com.image.manager.ApiConfig;
import com.image.mvp.view.ViewManager;
import com.image.ui.fragment.CollectionListFragment;
import com.image.ui.fragment.TabFragment;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl extends BasePresenterImpl<BaseModel, ViewManager.MainView> implements PresenterManager.MainPresenter {

    private Fragment dbFragment, mztFragment, mmFragment, meizituFragment, kkFragment, collectionFragment;

    public static final int FIRST_FRAGMENT = -1;

    private static final int TYPE_DB_FRAGMENT = 0;
    private static final String TYPE_DB_TAG = "DB";

    private static final int TYPE_MZT_FRAGMENT = 1;
    private static final String TYPE_MZT_TAG = "MZT";

    private static final int TYPE_MM_FRAGMENT = 2;
    private static final String TYPE_MM_TAG = "MM";

    private static final int TYPE_MEIZITU_FRAGMENT = 3;
    private static final String TYPE_MEIZITU_TAG = "MEIZITU";

    private static final int TYPE_KK_FRAGMENT = 4;
    private static final String TYPE_KK_TAG = "KK";

    private static final int TYPE_COLLECTION_FRAGMENT = 5;
    private static final String TYPE_COLLECTION_TAG = "COLLECTION";

    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.mzitu:
                setSelectFragment(TYPE_MZT_FRAGMENT);
                break;
            case FIRST_FRAGMENT:
            case R.id.dbmz:
                setSelectFragment(TYPE_DB_FRAGMENT);
                break;
            case R.id.mm:
                setSelectFragment(TYPE_MM_FRAGMENT);
                break;
            case R.id.meizitu:
                setSelectFragment(TYPE_MEIZITU_FRAGMENT);
                break;
            case R.id.kk:
                setSelectFragment(TYPE_KK_FRAGMENT);
                break;
            case R.id.collection:
                setSelectFragment(TYPE_COLLECTION_FRAGMENT);
                break;
            case R.id.search:
                view.switchSearch();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = view.getMainActivity().getSupportFragmentManager().findFragmentByTag(TYPE_DB_TAG);
        if (fragment != null && fragment.isHidden()) {
            setSelectFragment(TYPE_DB_FRAGMENT);
            view.selectMenuFirst();
        } else {
            view.onBack();
        }
    }

    @Override
    public void onMainDestroy() {
        if (null != dbFragment) {
            dbFragment = null;
        }
        if (null != mztFragment) {
            mztFragment = null;
        }
        if (null != mmFragment) {
            mmFragment = null;
        }
        if (null != meizituFragment) {
            meizituFragment = null;
        }
        if (null != kkFragment) {
            kkFragment = null;
        }
        if (null != collectionFragment) {
            collectionFragment = null;
        }
    }

    private void setSelectFragment(int type) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (type) {
            case TYPE_DB_FRAGMENT:
                dbFragment = manager.findFragmentByTag(TYPE_DB_TAG);
                hideFragment(transaction);
                if (null == dbFragment) {
                    dbFragment = TabFragment.newInstance(ApiConfig.Type.DOU_BAN_MEI_ZI);
                    transaction.add(R.id.fragment, dbFragment, TYPE_DB_TAG);
                } else {
                    transaction.show(dbFragment);
                }
                break;
            case TYPE_MZT_FRAGMENT:
                mztFragment = manager.findFragmentByTag(TYPE_MZT_TAG);
                hideFragment(transaction);
                if (null == mztFragment) {
                    mztFragment = TabFragment.newInstance(ApiConfig.Type.M_ZI_TU);
                    transaction.add(R.id.fragment, mztFragment, TYPE_MZT_TAG);
                } else {
                    transaction.show(mztFragment);
                }
                break;
            case TYPE_MM_FRAGMENT:
                mmFragment = manager.findFragmentByTag(TYPE_MM_TAG);
                hideFragment(transaction);
                if (null == mmFragment) {
                    mmFragment = TabFragment.newInstance(ApiConfig.Type.MM);
                    transaction.add(R.id.fragment, mmFragment, TYPE_MM_TAG);
                } else {
                    transaction.show(mmFragment);
                }
                break;
            case TYPE_MEIZITU_FRAGMENT:
                meizituFragment = manager.findFragmentByTag(TYPE_MEIZITU_TAG);
                hideFragment(transaction);
                if (null == meizituFragment) {
                    meizituFragment = TabFragment.newInstance(ApiConfig.Type.MEIZITU);
                    transaction.add(R.id.fragment, meizituFragment, TYPE_MEIZITU_TAG);
                } else {
                    transaction.show(meizituFragment);
                }
                break;
            case TYPE_KK_FRAGMENT:
                kkFragment = manager.findFragmentByTag(TYPE_KK_TAG);
                hideFragment(transaction);
                if (null == kkFragment) {
                    kkFragment = TabFragment.newInstance(ApiConfig.Type.KK);
                    transaction.add(R.id.fragment, kkFragment, TYPE_KK_TAG);
                } else {
                    transaction.show(kkFragment);
                }
                break;
            case TYPE_COLLECTION_FRAGMENT:
                collectionFragment = manager.findFragmentByTag(TYPE_COLLECTION_TAG);
                hideFragment(transaction);
                if (null == collectionFragment) {
                    collectionFragment = CollectionListFragment.newInstance();
                    transaction.add(R.id.fragment, collectionFragment, TYPE_COLLECTION_TAG);
                } else {
                    CollectionListFragment fragment = (CollectionListFragment) collectionFragment;
                    fragment.refreshUI();
                    transaction.show(collectionFragment);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragment(FragmentTransaction transaction) {
        if (null != dbFragment) {
            transaction.hide(dbFragment);
        }
        if (null != mztFragment) {
            transaction.hide(mztFragment);
        }
        if (null != mmFragment) {
            transaction.hide(mmFragment);
        }
        if (null != meizituFragment) {
            transaction.hide(meizituFragment);
        }
        if (null != kkFragment) {
            transaction.hide(kkFragment);
        }
        if (null != collectionFragment) {
            transaction.hide(collectionFragment);
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
