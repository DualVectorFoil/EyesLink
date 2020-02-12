package com.dualvectorfoil.eyeslink.di.module;

import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.api.HomeModel;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private HomeContract.IHomeView mView;

    public HomeModule(HomeContract.IHomeView view) {
        mView = view;
    }

    @Provides
    HomeContract.IHomeView getView() {
        return mView;
    }

    @Provides
    HomeContract.IHomeModel getModel(HomeModel model) {
        return model;
    }
}
