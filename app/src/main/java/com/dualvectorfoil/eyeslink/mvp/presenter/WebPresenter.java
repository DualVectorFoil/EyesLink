package com.dualvectorfoil.eyeslink.mvp.presenter;

import com.dualvectorfoil.eyeslink.mvp.contract.WebContract;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import javax.inject.Inject;

public class WebPresenter extends BasePresenter<WebContract.IWebModel, WebContract.IWebView> {

    private static final String TAG = "WebPresenter";

    @Inject
    public WebPresenter(WebContract.IWebModel model, WebContract.IWebView view) {
        super(model, view);
    }
}
