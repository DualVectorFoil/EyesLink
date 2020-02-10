package com.dualvectorfoil.eyeslink.di.module;

import com.dualvectorfoil.eyeslink.mvp.contract.MainContract;
import com.dualvectorfoil.eyeslink.mvp.model.api.MainModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainContract.BaseMainView mBaseMainView;

    public MainModule(MainContract.BaseMainView mainview) {
        mBaseMainView = mainview;
    }

    @Provides
    MainContract.BaseMainView getView() {
        return mBaseMainView;
    }

    @Provides
    MainContract.BaseMainModel getModel(MainModel model) {
        return model;
    }
}
