package com.dualvectorfoil.eyeslink.mvp.presenter;

import android.util.Log;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeContract.IHomeModel, HomeContract.IHomeView> {

    private static final String TAG = "HOME_TAG_presenter";

    @Inject
    public HomePresenter(HomeContract.IHomeModel model, HomeContract.IHomeView view) {
        super(model, view);
    }

    public boolean handleAddUrlInfo(String url, String name, String user, String password) {
        if ("".equals(name)) {
            name = url;
        }

        boolean isSuccess = true;
        if ("".equals(url)) {
            isSuccess = false;
        } else {
            isSuccess = mModel.addUrlInfo(url, name, user, password);
        }

        String toastMsg;
        if (isSuccess) {
            toastMsg = name + " 地址添加成功";
        } else {
            toastMsg = "信息填写错误，地址添加失败";
        }

        mView.showAddUrlInfoToast(toastMsg);

        return isSuccess;
    }
}
