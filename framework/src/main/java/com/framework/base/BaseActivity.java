package com.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.framework.App;
import com.framework.R;
import com.framework.utils.PermissionsUtils;
import com.framework.utils.UIUtils;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;
import com.socks.library.KLog;
import com.squareup.leakcanary.RefWatcher;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

/**
 * by y on 2016/7/26.
 */
public abstract class BaseActivity extends AppCompatActivity implements PermissionsUtils.PermissionsCallBack {

    private Disposable permissionsDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initById();
        initCreate(savedInstanceState);
        permissionsDisposable = PermissionsUtils.INSTANCE.requestPermission(this, PermissionsUtils.INSTANCE.getWRITE());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (permissionsDisposable != null) {
            RxJsoupDisposeManager.getInstance().unDispose(permissionsDisposable);
        }
        RefWatcher refWatcher = App.Companion.getRefWatcher();
        if (refWatcher != null) {
            KLog.i("leakcanary", String.format("watch %s", getClass().getSimpleName()));
            refWatcher.watch(this);
        }
    }

    @Override
    public void onPermissionsError() {
        UIUtils.INSTANCE.toast(getString(R.string.permission_denied));
    }

    @Override
    public void onPermissionsSuccess() {
    }

    @NotNull
    @Override
    public Activity getPermissionActivity() {
        return this;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }


    protected <T extends View> T getView(int id) {
        //noinspection unchecked
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

}
