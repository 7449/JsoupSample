package com.jsoup.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.framework.utils.MatcherUtils;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;
import com.rxjsoupnetwork.manager.RxJsoupNetWork;
import com.rxjsoupnetwork.manager.RxJsoupNetWorkListener;
import com.socks.library.KLog;

import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    private DisposableObserver<String> api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar contentLoadingProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        findViewById(R.id.test).setOnClickListener(v ->
                api = RxJsoupNetWork.getInstance().getApi(

                        RxJsoupNetWork.onSubscribe("http://www.mzitu.com/search/test/page/1/",
                                document -> {
                                    String a = document.select("div.nav-links").select("a").text();
                                    if (TextUtils.isEmpty(a)) {
                                        // 404 搜索没有数据
                                    } else {
                                        int anInt = MatcherUtils.getIntHasSpace(a);
                                    }
                                    return "Test";
                                })
                        ,
                        new RxJsoupNetWorkListener<String>() {
                            @Override
                            public void onNetWorkStart() {
                                contentLoadingProgressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNetWorkError(Throwable e) {
                                KLog.i("error:" + e.toString());
                                contentLoadingProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNetWorkComplete() {
                                contentLoadingProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNetWorkSuccess(String data) {

                            }
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (api != null) {
            RxJsoupDisposeManager.getInstance().unDispose(api);
        }
    }
}
