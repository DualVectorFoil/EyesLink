package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.webview.X5WebView;
import com.dualvectorfoil.eyeslink.di.component.DaggerMainComponent;
import com.dualvectorfoil.eyeslink.di.module.MainModule;
import com.dualvectorfoil.eyeslink.mvp.contract.MainContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.MainPresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;
import com.tencent.smtt.sdk.WebView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.BaseMainView {

//    private Button mBtn;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        mBtn = (Button) findViewById(R.id.test_btn);
//        mBtn.setOnClickListener((View) -> mPresenter.getList());
        mWebView = X5WebView.getInstance().getWebView();
        FrameLayout layout = (FrameLayout) findViewById(R.id.layout_webview);
        layout.addView(mWebView);
        mWebView.loadUrl("http://www.baidu.com/");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void showToast(String msg) {
        Log.d("xixi222", "msg: " + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                X5WebView.getInstance().removeWebView();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        X5WebView.getInstance().removeWebView();
    }
}
