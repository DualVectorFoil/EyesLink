package com.dualvectorfoil.eyeslink.mvp.presenter;

import android.util.Log;

import com.dualvectorfoil.eyeslink.mvp.contract.MainContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.MainEntity;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class MainPresenter extends BasePresenter<MainContract.BaseMainModel, MainContract.BaseMainView> {

    @Inject
    public MainPresenter(MainContract.BaseMainModel model, MainContract.BaseMainView view) {
        super(model, view);
    }

    public void getList() {
        List<MainEntity> list = mModel.getList();
        for (MainEntity entity : list) {
            mView.showToast(entity.getY());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO
        Log.d("MainPresenter", "onDestroy");
    }
}
