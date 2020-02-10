package com.dualvectorfoil.eyeslink.mvp.ui.base;

public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
    }

    public BasePresenter(V view) {
        this.mView = view;
    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            mModel.onDestroy();
        }
        this.mModel = null;
        this.mView = null;
    }
}
