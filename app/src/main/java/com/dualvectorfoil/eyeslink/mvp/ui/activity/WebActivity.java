package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.BuildConfig;
import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.constants.Constants;
import com.dualvectorfoil.eyeslink.app.webview.X5WebView;
import com.dualvectorfoil.eyeslink.di.component.DaggerWebComponent;
import com.dualvectorfoil.eyeslink.di.module.WebModule;
import com.dualvectorfoil.eyeslink.mvp.contract.WebContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.WebPresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;
import com.dualvectorfoil.eyeslink.util.PermissionUtils;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebView;

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.IWebView {

    private static final String TAG = "WEB_TAG_activity";

    private WebView mWebView;

    private String mUrl;
    private String mName;
    private String mUser;
    private String mPassword;

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
        PermissionUtils.verifyStoragePermissions(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.WEB_ACTIVITY_BUNDLE_KEY);
        if (bundle == null) {
            Log.e(TAG, "bundle is null, finish self");
            finish();
            return;
        }
        mUrl = bundle.getString("url");
        if (mUrl == null || "".equals(mUrl)) {
            Log.e(TAG, "url is null, finish self");
            finish();
            return;
        }
        mName = bundle.getString("name");
        mUser = bundle.getString("user");
        mPassword = bundle.getString("password");
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "url: " + mUrl + ", name: " + mName + ", user: " + mUser + ", password: " + mPassword);
        }

        mWebView = X5WebView.getInstance().create(this);
//        if (TbsVideo.canUseTbsPlayer(this)) {
//            TbsVideo.openVideo(this, "https://m.bilibili.com/video/av63557980");
//        }
        mWebView.loadUrl("https://www.baidu.com");
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
}
