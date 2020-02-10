package com.dualvectorfoil.eyeslink.di.component;

import com.dualvectorfoil.eyeslink.di.module.MainModule;
import com.dualvectorfoil.eyeslink.mvp.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity activity);
}
