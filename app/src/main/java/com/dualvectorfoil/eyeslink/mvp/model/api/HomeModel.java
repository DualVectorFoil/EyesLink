package com.dualvectorfoil.eyeslink.mvp.model.api;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;

import javax.inject.Inject;

public class HomeModel implements HomeContract.IHomeModel {

    private static final String TAG = "HOME_TAG_model";

    // TODO database

    @Inject
    public HomeModel() {
    }

    @Override
    public void addUrlInfo() {

    }

    @Override
    public void onDestroy() {
        // TODO
    }
}
