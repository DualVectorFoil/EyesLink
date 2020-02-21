package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.di.component.DaggerWebComponent;
import com.dualvectorfoil.eyeslink.di.module.WebModule;
import com.dualvectorfoil.eyeslink.mvp.contract.WebContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.WebPresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.IWebView {

    private static final String TAG = "WEB_TAG_activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerWebComponent.builder().webModule(new WebModule(this)).build().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
