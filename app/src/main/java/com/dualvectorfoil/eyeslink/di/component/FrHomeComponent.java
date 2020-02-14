package com.dualvectorfoil.eyeslink.di.component;

import com.dualvectorfoil.eyeslink.di.module.FrHomeModule;
import com.dualvectorfoil.eyeslink.mvp.ui.fragment.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = FrHomeModule.class)
public interface FrHomeComponent {

    void inject(HomeFragment fragment);
}
