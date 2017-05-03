package com.jsoup.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import com.framework.utils.MatcherUtils
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager
import com.rxjsoupnetwork.manager.RxJsoupNetWork
import com.rxjsoupnetwork.manager.RxJsoupNetWorkListener
import com.socks.library.KLog
import io.reactivex.observers.DisposableObserver

class MainActivity : AppCompatActivity() {

    private var api: DisposableObserver<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contentLoadingProgressBar = findViewById(R.id.progress_bar) as ProgressBar

        findViewById(R.id.test).setOnClickListener {
            api = RxJsoupNetWork.getInstance().getApi(

                    RxJsoupNetWork.onSubscribe("http://www.mzitu.com/search/test/page/1/",
                            RxJsoupNetWork.DocumentCallback { document ->
                                val a = document.select("div.nav-links").select("a").text()
                                if (TextUtils.isEmpty(a)) {
                                    // 404 搜索没有数据
                                } else {
                                    val anInt = MatcherUtils.getIntHasSpace(a)!!
                                }
                                "Test"
                            }),
                    object : RxJsoupNetWorkListener<String> {
                        override fun onNetWorkStart() {
                            contentLoadingProgressBar.visibility = View.VISIBLE
                        }

                        override fun onNetWorkError(e: Throwable) {
                            KLog.i("error:" + e.toString())
                            contentLoadingProgressBar.visibility = View.GONE
                        }

                        override fun onNetWorkComplete() {
                            contentLoadingProgressBar.visibility = View.GONE
                        }

                        override fun onNetWorkSuccess(data: String) {

                        }
                    }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (api != null) {
            RxJsoupDisposeManager.getInstance().unDispose(api!!)
        }
    }
}
