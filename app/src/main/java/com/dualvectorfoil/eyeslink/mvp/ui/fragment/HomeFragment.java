package com.dualvectorfoil.eyeslink.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.di.component.DaggerFrHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.FrHomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.FrHomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment<FrHomePresenter> implements FrHomeContract.IFrHomeView {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerFrHomeComponent.builder().frHomeModule(new FrHomeModule(this)).build().inject(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
