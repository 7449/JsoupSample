package com.magnetic.mvp.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;
import com.magnetic.manager.MaYiJsoupManager;
import com.magnetic.manager.NiMaJsoupManager;
import com.magnetic.manager.YingTaoJsoupManager;
import com.magnetic.manager.ZhiZhuJsoupManager;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.view.ViewManager;
import com.magnetic.ui.fragment.MagneticListFragment;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.jsoup.network.manager.RxJsoupNetWork;
import io.reactivex.jsoup.network.manager.RxJsoupNetWorkListener;

/**
 * by y on 2017/6/6.
 */

public class MagneticListPresenterImpl extends BasePresenterImpl<List<MagneticModel>, ViewManager.MagneticListView> implements PresenterManager.MagneticListPresenter {

    private int position;

    public MagneticListPresenterImpl(ViewManager.MagneticListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String search, int tabPosition, int page) {
        this.position = tabPosition;
        String url = null;
        switch (tabPosition) {
            case 0:
                url = String.format(ApiConfig.MA_YI_URL, search, page + 1);
                break;
            case 1:
                url = String.format(ApiConfig.YING_TAO_URL, search, page + 1);
                break;
            case 2:
                url = String.format(ApiConfig.NI_MA_URL, search, page + 1);
                break;
            case 3:
                url = String.format(ApiConfig.ZHI_ZHU_URL, search, page + 1);
                break;
            default:
                break;
        }
        netWork(url);
    }

    @Override
    public void netWorkZhiZhuMagnetic(@NonNull String url) {
        RxJsoupNetWork
                .getInstance()
                .getApi(MagneticListFragment.ZHIZHU_TAG, url,
                        new RxJsoupNetWorkListener<MagneticModel>() {
                            @Override
                            public void onNetWorkStart() {
                                if (view != null) {
                                    view.showProgress();
                                }
                            }

                            @Override
                            public void onNetWorkError(Throwable e) {
                                if (view != null) {
                                    view.netWorkError();
                                    view.hideProgress();
                                }
                                UIUtils.toast(UIUtils.getString(R.string.network_error));
                                RxJsoupNetWork.getInstance().cancel(MagneticListFragment.ZHIZHU_TAG);
                            }

                            @Override
                            public void onNetWorkComplete() {
                                if (view != null) {
                                    view.hideProgress();
                                }
                                RxJsoupNetWork.getInstance().cancel(MagneticListFragment.ZHIZHU_TAG);
                            }

                            @Override
                            public void onNetWorkSuccess(MagneticModel magneticModel) {
                                if (view != null) {
                                    view.zhizhuMagnetic(magneticModel);
                                }
                            }


                            @Override
                            public MagneticModel getT(Document document) {
                                return ZhiZhuJsoupManager.get(document).getDetail();
                            }
                        });
    }

    @Override
    public List<MagneticModel> getT(Document document) {
        switch (position) {
            case 0:
                return MaYiJsoupManager.get(document).getList();
            case 1:
                return YingTaoJsoupManager.get(document).getList();
            case 2:
                return NiMaJsoupManager.get(document).getList();
            case 3:
                return ZhiZhuJsoupManager.get(document).getList();
            default:
                return new ArrayList<>();
        }
    }
}
