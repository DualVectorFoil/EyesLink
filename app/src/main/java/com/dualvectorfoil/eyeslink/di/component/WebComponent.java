package com.dualvectorfoil.eyeslink.di.component;

import com.dualvectorfoil.eyeslink.di.module.WebModule;
import com.dualvectorfoil.eyeslink.mvp.ui.activity.WebActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = WebModule.class)
public interface WebComponent {

    void inject(WebActivity activity);
}
