package com.dualvectorfoil.eyeslink.mvp.model.api;

import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class FrHomeModel implements FrHomeContract.IFrHomeModel {

    private static final String TAG = "FR_HOME_TAG_model";

    private Realm mDB;

    @Inject
    public FrHomeModel() {
        mDB = Realm.getDefaultInstance();
    }

    @Override
    public List<UrlInfo> getUrlInfoItemViewList() {
        return new ArrayList<UrlInfo>(mDB.where(UrlInfo.class).findAll());
    }

    @Override
    public void onDestroy() {
        mDB = null;
    }
}
