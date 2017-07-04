package com.magnetic.mvp.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.framework.base.mvp.BasePresenterImpl;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.view.ViewManager;
import com.magnetic.ui.fragment.CollectionFragment;
import com.magnetic.ui.fragment.TabFragment;

import org.jsoup.nodes.Document;

import io.reactivex.jsoup.network.bus.RxBus;

/**
 * by y on 2017/6/6.
 */

public class MainPresenterImpl extends BasePresenterImpl<MagneticModel, ViewManager.MainView> implements PresenterManager.MainPresenter {

    private static final String TYPE_LIST_TAG = "LIST";
    private static final String TYPE_COLLECTION_TAG = "COLLECTION";

    private Fragment listFragment, collectionFragment;

    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void startSearch(String search) {
        setSelectFragment(search);
    }

    @Override
    public void onBackPressed() {
        FragmentManager supportFragmentManager = view.getMainActivity().getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentByTag(TYPE_LIST_TAG);
        if (listFragment == null && collectionFragment != null && !collectionFragment.isHidden()) {
            view.setToolBar();
            supportFragmentManager.beginTransaction().remove(collectionFragment).commit();
            onMainDestroy();
            return;
        }
        if (fragment != null && fragment.isHidden()) {
            setSelectFragment(TYPE_LIST_TAG);
        } else {
            view.onBack();
        }
    }

    @Override
    public void onMainDestroy() {
        if (listFragment != null) {
            listFragment = null;
        }
        if (collectionFragment != null) {
            collectionFragment = null;
        }
    }

    private void setSelectFragment(String search) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (search == null) {
            collectionFragment = manager.findFragmentByTag(TYPE_COLLECTION_TAG);
            hideFragment(transaction);
            if (null == collectionFragment) {
                collectionFragment = CollectionFragment.newInstance();
                transaction.add(R.id.fragment, collectionFragment, TYPE_COLLECTION_TAG);
            } else {
                transaction.show(collectionFragment);
            }
        } else {
            view.setToolBar();
            listFragment = manager.findFragmentByTag(TYPE_LIST_TAG);
            hideFragment(transaction);
            if (null == listFragment) {
                listFragment = TabFragment.newInstance(search);
                transaction.add(R.id.fragment, listFragment, TYPE_LIST_TAG);
            } else {
                if (!TextUtils.equals(search, TYPE_LIST_TAG)) {
                    RxBus.getInstance().post(ApiConfig.BUS, search);
                }
                transaction.show(listFragment);
            }
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (null != listFragment) {
            transaction.hide(listFragment);
        }
        if (null != collectionFragment) {
            transaction.hide(collectionFragment);
        }
    }

    @Override
    public MagneticModel getT(Document document) {
        return null;
    }
}
