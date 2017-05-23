package com.fiction.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.fiction.adapter.TabAdapter;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionHomeManager;
import com.framework.R;
import com.framework.base.BaseFragment;

import io.reactivex.jsoup.network.bus.RxBus;
import io.reactivex.jsoup.network.bus.RxBusCallBack;


/**
 * by y on 2016/7/28.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

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

        RxBus.getInstance()
                .register(
                        ApiConfig.Type.BI_QU_GE,
                        new RxBusCallBack<String>() {
                            @Override
                            public void onBusNext(String s) {
                                switch (s) {
                                    case JsoupFictionHomeManager.TYPE_TITLE_XUAN_HUAN:
                                        viewPager.setCurrentItem(1);
                                        break;
                                    case JsoupFictionHomeManager.TYPE_TITLE_XIU_ZHEN:
                                        viewPager.setCurrentItem(2);
                                        break;
                                    case JsoupFictionHomeManager.TYPE_TITLE_DU_SHI:
                                        viewPager.setCurrentItem(3);
                                        break;
                                    case JsoupFictionHomeManager.TYPE_TITLE_CHUAN_YUE:
                                        viewPager.setCurrentItem(4);
                                        break;
                                    case JsoupFictionHomeManager.TYPE_TITLE_WANG_YOU:
                                        viewPager.setCurrentItem(5);
                                        break;
                                    case JsoupFictionHomeManager.TYPE_TITLE_KE_HUAN:
                                        viewPager.setCurrentItem(6);
                                        break;
                                }
                            }

                            @Override
                            public void onBusError(Throwable throwable) {

                            }

                            @Override
                            public Class<String> busOfType() {
                                return String.class;
                            }
                        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unregister(ApiConfig.Type.BI_QU_GE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }
}
