package com.dualvectorfoil.eyeslink.mvp.model.api;

import android.util.Log;

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
                UrlInfo res = mDB.where(UrlInfo.class).equalTo("mUrl", url).findFirst();
                if (res != null) {
                    Log.w(TAG, "Add url info failed, url has exsited");
                    return;
                }
                realm.createObject(UrlInfo.class, url)
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
