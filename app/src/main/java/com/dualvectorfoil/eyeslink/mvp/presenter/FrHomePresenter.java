package com.dualvectorfoil.eyeslink.mvp.presenter;

import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

public class FrHomePresenter extends BasePresenter<FrHomeContract.IFrHomeModel, FrHomeContract.IFrHomeView> {

    private static final String TAG = "FR_HOME_TAG_presenter";

    @Inject
    public FrHomePresenter(FrHomeContract.IFrHomeModel model, FrHomeContract.IFrHomeView view) {
        super(model, view);
    }

    public List<UrlInfo> getUrlInfoItemViewList() {
        return mModel.getUrlInfoItemViewList();
    }
}
