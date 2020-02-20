package com.dualvectorfoil.eyeslink.mvp.model.api;

import android.util.Log;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;

import javax.inject.Inject;

import io.realm.Realm;

public class HomeModel implements HomeContract.IHomeModel {

    private static final String TAG = "HOME_TAG_model";

    private boolean mAddUrlInfoSucess = false;

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

        mAddUrlInfoSucess = false;
        mDB.executeTransaction((Realm realm) -> {
            UrlInfo res = mDB.where(UrlInfo.class).equalTo("mUrl", url).findFirst();
            if (res != null) {
                Log.w(TAG, "Add url info failed, url has exsited");
            } else {
                int index = mDB.where(UrlInfo.class).findAll().size();
                realm.createObject(UrlInfo.class, url)
                        .setname(name)
                        .setuser(user)
                        .setpassword(password)
                        .setIndex(index);
                mAddUrlInfoSucess = true;
            }
        });
        return mAddUrlInfoSucess;
    }

    @Override
    public void onDestroy() {
        mDB = null;
    }
}
