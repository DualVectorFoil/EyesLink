package com.dualvectorfoil.eyeslink.mvp.model.api;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;

import javax.inject.Inject;

import io.realm.Realm;

public class HomeModel implements HomeContract.IHomeModel {

    private static final String TAG = "HOME_TAG_model";

    private Realm mDB;

    @Inject
    public HomeModel() {
        mDB = Realm.getDefaultInstance();
    }

    @Override
    public boolean addUrlInfo(String url, String name, String user, String password) {
        if ("".equals(user) && !"".equals(password) || "".equals(password) && !"".equals(user)) {
            return false;
        }

        mDB.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createObject(UrlInfo.class)
                        .seturl(url)
                        .setname(name)
                        .setuser(user)
                        .setpassword(password);
            }
        });
        return true;
    }

    @Override
    public void onDestroy() {
        mDB = null;
    }
}
