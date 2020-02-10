package com.dualvectorfoil.eyeslink.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.utils.DialogUtils;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity<V extends IView, P extends BasePresenter<V>> extends RxAppCompatActivity implements IView, IActivity {

    protected P mPresenter;
    protected Dialog mDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView((V) this);
        mDialog = DialogUtils.createLoadingDialog(this, "请稍后...");
        initView();
        initData(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
