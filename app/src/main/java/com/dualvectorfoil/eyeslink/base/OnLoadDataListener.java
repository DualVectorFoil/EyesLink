package com.dualvectorfoil.eyeslink.base;

public interface OnLoadDataListener<T> {

    void onSuccess(T t);

    void onFailure(T t);
}
