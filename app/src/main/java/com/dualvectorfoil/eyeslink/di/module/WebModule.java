package com.dualvectorfoil.eyeslink.di.module;

import com.dualvectorfoil.eyeslink.mvp.contract.WebContract;
import com.dualvectorfoil.eyeslink.mvp.model.api.WebModel;

import dagger.Module;
import dagger.Provides;

@Module
public class WebModule {

    private WebContract.IWebView mView;

    public WebModule(WebContract.IWebView view) {
        mView = view;
    }

    @Provides
    WebContract.IWebView getView() {
        return mView;
    }

    @Provides
    WebContract.IWebModel getModel(WebModel model) {
        return model;
    }
}
