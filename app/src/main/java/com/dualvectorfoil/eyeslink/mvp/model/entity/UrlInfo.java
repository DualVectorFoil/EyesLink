package com.dualvectorfoil.eyeslink.mvp.model.entity;

import io.realm.RealmObject;

public class UrlInfo extends RealmObject {

    private String mUrl;
    private String mName;
    private String mUser;
    private String mPassword;

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
}
