package com.dualvectorfoil.eyeslink.di.module;

import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.api.FrHomeModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FrHomeModule {

    private FrHomeContract.IFrHomeView mView;

    public FrHomeModule(FrHomeContract.IFrHomeView view) {
        mView = view;
    }

    @Provides
    FrHomeContract.IFrHomeView getView() {
        return mView;
    }

    @Provides
    FrHomeContract.IFrHomeModel getModel(FrHomeModel model) {
        return model;
    }
}
