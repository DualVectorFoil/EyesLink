package com.dualvectorfoil.eyeslink.mvp.ui.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.util.DialogUtils;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IView, IActivity {

    @Inject
    protected P mPresenter;
    protected Dialog mDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mDialog = DialogUtils.createLoadingDialog(this, "请稍后...");
        initView();
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
