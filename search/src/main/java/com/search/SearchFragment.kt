package com.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.AppCompatEditText
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.widget.LinearLayout

/**
 * by y on 2017/4/19
 */

class SearchFragment : Fragment(), View.OnClickListener, ViewTreeObserver.OnPreDrawListener {


    private var mSearchLayout: LinearLayout? = null
    private var etSearch: AppCompatEditText? = null

    private var searchInterface: SearchToActivityInterface? = null

    fun init(searchInterface: SearchToActivityInterface): SearchFragment {
        this.searchInterface = searchInterface
        return this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater!!.inflate(R.layout.fragment_toolbar_search, container, false)
        mSearchLayout = inflate.findViewById(R.id.reveal_linear_layout) as LinearLayout
        etSearch = inflate.findViewById(R.id.et_search) as AppCompatEditText

        inflate.findViewById(R.id.iv_search).setOnClickListener(this)
        inflate.findViewById(R.id.iv_finish).setOnClickListener(this)
        inflate.findViewById(R.id.rootView).setOnClickListener(this)

        mSearchLayout!!.viewTreeObserver.addOnPreDrawListener(this)
        return inflate
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.rootView || i == R.id.iv_finish) {
            onBack()
        } else if (i == R.id.iv_search && searchInterface != null) {
            val trim = etSearch!!.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(trim)) {
                searchInterface!!.emptySearch()
            } else {
                Utils.offKeyboard(etSearch!!)
                etSearch!!.text.clear()
                searchInterface!!.onSearchStart(trim)
                activity.supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onPreDraw(): Boolean {
        mSearchLayout!!.viewTreeObserver.removeOnPreDrawListener(this)
        Utils.startAnimator(mSearchLayout!!,
                object : Utils.SimpleAnimationListener() {
                    override fun onAnimationStart(animation: Animation) {
                        super.onAnimationStart(animation)
                        mSearchLayout!!.visibility = View.GONE
                    }

                    override fun onAnimationEnd(animation: Animation) {
                        super.onAnimationEnd(animation)
                        etSearch!!.requestFocus()
                        Utils.openKeyboard(etSearch!!)
                        mSearchLayout!!.visibility = View.VISIBLE
                        mSearchLayout!!.clearAnimation()
                    }
                })
        return true
    }

    fun onBack() {
        if (mSearchLayout!!.visibility != View.GONE) {
            Utils.endAnimator(mSearchLayout!!,
                    object : Utils.SimpleAnimationListener() {

                        override fun onAnimationStart(animation: Animation) {
                            super.onAnimationStart(animation)
                            Utils.offKeyboard(etSearch!!)
                        }

                        override fun onAnimationEnd(animation: Animation) {
                            super.onAnimationEnd(animation)
                            mSearchLayout!!.visibility = View.GONE
                            mSearchLayout!!.clearAnimation()
                            if (activity != null)
                                activity.supportFragmentManager.popBackStack()
                        }
                    })
        }
    }

    companion object {

        val SEARCH_TAG = "SEARCH"


        fun startFragment(activity: FragmentActivity, searchInterface: SearchToActivityInterface) {
            activity.supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, SearchFragment().init(searchInterface), SEARCH_TAG)
                    .addToBackStack("fragment:reveal")
                    .commit()
        }
    }
}
