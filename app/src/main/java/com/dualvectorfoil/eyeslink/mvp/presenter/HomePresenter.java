package com.dualvectorfoil.eyeslink.mvp.presenter;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import javax.inject.Inject;

public class HomePresenter extends BasePresenter<HomeContract.IHomeModel, HomeContract.IHomeView> {

    private static final String TAG = "HOME_TAG_presenter";

    @Inject
    public HomePresenter(HomeContract.IHomeModel model, HomeContract.IHomeView view) {
        super(model, view);
    }

    public void handleAddUrlInfo() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO
    }
}
