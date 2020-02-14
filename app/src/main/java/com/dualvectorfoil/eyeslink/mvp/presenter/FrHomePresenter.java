package com.dualvectorfoil.eyeslink.mvp.presenter;

import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import javax.inject.Inject;

public class FrHomePresenter extends BasePresenter<FrHomeContract.IFrHomeModel, FrHomeContract.IFrHomeView> {

    private static final String TAG = "FR_HOME_TAG_presenter";

    @Inject
    public FrHomePresenter(FrHomeContract.IFrHomeModel model, FrHomeContract.IFrHomeView view) {
        super(model, view);
    }
}
