package com.dualvectorfoil.eyeslink.mvp.ui.base;

public interface OnLoadDataListener<T> {

    void onSuccess(T t);

    void onFailure(T t);
}
