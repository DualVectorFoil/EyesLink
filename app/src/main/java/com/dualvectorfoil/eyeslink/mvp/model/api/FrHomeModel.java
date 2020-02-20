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
        return new ArrayList<UrlInfo>(mDB.where(UrlInfo.class).findAll().sort("mIndex"));
    }

    @Override
    public boolean deleteUrlInfo(UrlInfo urlInfo) {
        UrlInfo res = mDB.where(UrlInfo.class).equalTo("mUrl",  urlInfo.geturl()).findFirst();
        if (res != null) {
            mDB.executeTransaction((Realm) -> res.deleteFromRealm());
        }
        return true;
    }

    @Override
    public void onChangeUrlInfoItemIndex(UrlInfo urlInfo, int newIndex) {
        mDB.executeTransaction((Realm) -> urlInfo.setIndex(newIndex));
    }

    @Override
    public void handleUrlInfoItemEdit(UrlInfo urlInfo, String name, String user, String password) {
        mDB.executeTransaction((Realm) -> {
            urlInfo.setname(name);
            urlInfo.setuser(user);
            urlInfo.setpassword(password);
        });
    }

    @Override
    public void onDestroy() {
        mDB = null;
    }
}
