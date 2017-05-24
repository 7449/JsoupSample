package com.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.framework.R;
import com.framework.base.mvp.PresenterImplCompat;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity<P extends PresenterImplCompat> extends AppCompatActivity {


    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initById();
        mPresenter = initPresenterImpl();
        initCreate(savedInstanceState);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }


    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract P initPresenterImpl();

    protected abstract int getLayoutId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
