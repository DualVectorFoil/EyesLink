package com.dualvectorfoil.eyeslink.di.component;

import com.dualvectorfoil.eyeslink.di.module.HomeModule;
import com.dualvectorfoil.eyeslink.mvp.ui.activity.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = HomeModule.class)
public interface HomeComponent {

    void inject(HomeActivity activity);
}
