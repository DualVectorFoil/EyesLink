package com.dualvectorfoil.eyeslink.mvp.model.entity;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class UrlInfo extends RealmObject {

    @Required
    private String mUrl;
    @Required
    private String mName;
    private String mUser;
    private String mPassword;
    private int mResId = -1;

    public String geturl() {
        return mUrl;
    }

    public UrlInfo seturl(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public String getname() {
        return mName;
    }

    public UrlInfo setname(String mName) {
        this.mName = mName;
        return this;
    }

    public String getuser() {
        return mUser;
    }

    public UrlInfo setuser(String mUser) {
        this.mUser = mUser;
        return this;
    }

    public String getpassword() {
        return mPassword;
    }

    public UrlInfo setpassword(String mPassword) {
        this.mPassword = mPassword;
        return this;
    }

    public void setResId(int id) {
        this.mResId = id;
    }

    public int getResId() {
        return mResId;
    }
}
