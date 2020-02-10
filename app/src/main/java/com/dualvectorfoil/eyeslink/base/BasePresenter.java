package com.dualvectorfoil.eyeslink.base;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IView> {

    protected V mView;
    private WeakReference<V> mWeakReferenceView;

    protected V getView() {
        if (mView == null) {
            throw new IllegalStateException("view not attached");
        } else {
            return mView;
        }
    }

    public void attachView(V view) {
        if (view != null) {
            mWeakReferenceView = new WeakReference<V>(view);
            this.mView = mWeakReferenceView.get();
        }
    }

    public void detachView() {
        mWeakReferenceView.clear();
        mWeakReferenceView = null;
        mView = null;
    }
}
