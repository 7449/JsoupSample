package com.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.rxjsoupnetwork.manager.RxJsoupSubscriptionManager;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
    }

    public void replaceFragment(int frameLayoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(frameLayoutId, fragment).commit();
    }


    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxJsoupSubscriptionManager.getInstance().unSubscription();
    }
}
