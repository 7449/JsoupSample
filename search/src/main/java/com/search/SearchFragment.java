package com.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.LinearLayout;

/**
 * by y on 2017/4/19
 */

public class SearchFragment extends Fragment
        implements View.OnClickListener, ViewTreeObserver.OnPreDrawListener {


    private LinearLayout mSearchLayout;
    private AppCompatEditText etSearch;

    private SearchToActivityInterface searchInterface;

    public static final String SEARCH_TAG = "SEARCH";


    public static void startFragment(FragmentActivity activity, SearchToActivityInterface searchInterface) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new SearchFragment().init(searchInterface), SEARCH_TAG)
                .addToBackStack("fragment:reveal")
                .commit();
    }

    public SearchFragment init(SearchToActivityInterface searchInterface) {
        this.searchInterface = searchInterface;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_toolbar_search, container, false);
        mSearchLayout = (LinearLayout) inflate.findViewById(R.id.reveal_linear_layout);
        etSearch = (AppCompatEditText) inflate.findViewById(R.id.et_search);

        inflate.findViewById(R.id.iv_search).setOnClickListener(this);
        inflate.findViewById(R.id.iv_finish).setOnClickListener(this);
        inflate.findViewById(R.id.rootView).setOnClickListener(this);

        mSearchLayout.getViewTreeObserver().addOnPreDrawListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rootView || i == R.id.iv_finish) {
            onBack();
        } else if (i == R.id.iv_search && searchInterface != null) {
            String trim = etSearch.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                searchInterface.emptySearch();
            } else {
                Utils.offKeyboard(etSearch);
                etSearch.getText().clear();
                searchInterface.onSearchStart(trim);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onPreDraw() {
        mSearchLayout.getViewTreeObserver().removeOnPreDrawListener(this);
        Utils.startAnimator(mSearchLayout,
                new Utils.SimpleAnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        super.onAnimationStart(animation);
                        mSearchLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        etSearch.requestFocus();
                        Utils.openKeyboard(etSearch);
                        mSearchLayout.setVisibility(View.VISIBLE);
                        mSearchLayout.clearAnimation();
                    }
                });
        return true;
    }

    public void onBack() {
        if (mSearchLayout.getVisibility() != View.GONE) {
            Utils.endAnimator(mSearchLayout,
                    new Utils.SimpleAnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            super.onAnimationStart(animation);
                            Utils.offKeyboard(etSearch);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            super.onAnimationEnd(animation);
                            mSearchLayout.setVisibility(View.GONE);
                            mSearchLayout.clearAnimation();
                            if (getActivity() != null)
                                getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
        }
    }
}
