package com.dualvectorfoil.eyeslink.mvp.model.api;

import com.dualvectorfoil.eyeslink.mvp.contract.WebContract;

import javax.inject.Inject;

import io.realm.Realm;

public class WebModel implements WebContract.IWebModel {

    private static final String TAG = "HOME_TAG_model";

    private Realm mDB;

    @Inject
    public WebModel() {
        mDB = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {

    }
}
