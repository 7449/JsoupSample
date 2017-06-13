package com.magnetic.mvp.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.framework.base.mvp.BasePresenterImpl;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.view.ViewManager;
import com.magnetic.ui.fragment.TabFragment;

import org.jsoup.nodes.Document;

import io.reactivex.jsoup.network.bus.RxBus;

/**
 * by y on 2017/6/6.
 */

public class MainPresenterImpl extends BasePresenterImpl<MagneticModel, ViewManager.MainView> implements PresenterManager.MainPresenter {

    private static final String TYPE_LIST_TAG = "LIST";
    private Fragment listFragment;

    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void startSearch(String search) {
        setSelectFragment(search);
    }

    @Override
    public void onMainDestroy() {
        if (listFragment != null) {
            listFragment = null;
        }
    }

    private void setSelectFragment(String search) {
        FragmentManager manager = view.getMainActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        listFragment = manager.findFragmentByTag(TYPE_LIST_TAG);
        hideFragment(transaction);
        if (null == listFragment) {
            listFragment = TabFragment.newInstance(search);
            transaction.add(R.id.fragment, listFragment, TYPE_LIST_TAG);
        } else {
            RxBus.getInstance().post(ApiConfig.BUS, search);
            transaction.show(listFragment);
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (null != listFragment) {
            transaction.hide(listFragment);
        }
    }

    @Override
    public MagneticModel getT(Document document) {
        return null;
    }
}
