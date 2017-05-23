package com.fiction.main.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeHomeManager;
import com.fiction.manager.JsoupZwHomeManager;
import com.framework.R;
import com.framework.base.BaseFragment;
import com.rxjsoupnetwork.bus.RxBus;
import com.rxjsoupnetwork.bus.RxBusCallBack;

import io.reactivex.observers.DisposableObserver;


/**
 * by y on 2016/7/28.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DisposableObserver<String> tabSelectZWObserver;
    private DisposableObserver<String> tabSelectBiQuGeObserver;

    public static TabFragment newInstance(String type) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, type);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    protected void initBundle() {
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        tabLayout = getView(R.id.tab_layout);
        viewPager = getView(R.id.viewPager);
    }

    @Override
    protected void initActivityCreated() {
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager(), type));
        tabLayout.setupWithViewPager(viewPager);

        tabSelectBiQuGeObserver =
                RxBus.getInstance()
                        .toObservable(
                                ApiConfig.Type.BI_QU_GE,
                                String.class,
                                new RxBusCallBack<String>() {
                                    @Override
                                    public void onNext(String data) {
                                        switch (data) {
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_XUAN_HUAN:
                                                viewPager.setCurrentItem(1);
                                                break;
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_XIU_ZHEN:
                                                viewPager.setCurrentItem(2);
                                                break;
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_DU_SHI:
                                                viewPager.setCurrentItem(3);
                                                break;
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_CHUAN_YUE:
                                                viewPager.setCurrentItem(4);
                                                break;
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_WANG_YOU:
                                                viewPager.setCurrentItem(5);
                                                break;
                                            case JsoupBiQuGeHomeManager.TYPE_TITLE_KE_HUAN:
                                                viewPager.setCurrentItem(6);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {

                                    }
                                });
        tabSelectZWObserver =
                RxBus.getInstance()
                        .toObservable(
                                ApiConfig.Type.ZW_81,
                                String.class,
                                new RxBusCallBack<String>() {
                                    @Override
                                    public void onNext(String data) {
                                        switch (data) {
                                            case JsoupZwHomeManager.TYPE_TITLE_XUAN_HUAN:
                                                viewPager.setCurrentItem(1);
                                                break;
                                            case JsoupZwHomeManager.TYPE_TITLE_XIU_ZHEN:
                                                viewPager.setCurrentItem(2);
                                                break;
                                            case JsoupZwHomeManager.TYPE_TITLE_DU_SHI:
                                                viewPager.setCurrentItem(3);
                                                break;
                                            case JsoupZwHomeManager.TYPE_TITLE_LI_SHI:
                                                viewPager.setCurrentItem(4);
                                                break;
                                            case JsoupZwHomeManager.TYPE_TITLE_WANG_YOU:
                                                viewPager.setCurrentItem(5);
                                                break;
                                            case JsoupZwHomeManager.TYPE_TITLE_KE_HUAN:
                                                viewPager.setCurrentItem(6);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {

                                    }
                                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tabSelectZWObserver != null) {
            RxBus.getInstance().unregister(ApiConfig.Type.ZW_81, tabSelectZWObserver);
        }
        if (tabSelectBiQuGeObserver != null) {
            RxBus.getInstance().unregister(ApiConfig.Type.BI_QU_GE, tabSelectBiQuGeObserver);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }
}
