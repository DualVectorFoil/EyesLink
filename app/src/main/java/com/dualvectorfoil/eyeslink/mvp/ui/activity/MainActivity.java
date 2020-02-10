package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.di.component.DaggerMainComponent;
import com.dualvectorfoil.eyeslink.di.module.MainModule;
import com.dualvectorfoil.eyeslink.mvp.contract.MainContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.MainPresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.BaseMainView {

    private Button mBtn;

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
        mBtn = (Button) findViewById(R.id.test_btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getList();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void showToast(String msg) {
        Log.d("xixi222", "msg: " + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
