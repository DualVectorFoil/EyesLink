package com.dualvectorfoil.eyeslink.mvp.model.api;

import android.util.Log;

import com.dualvectorfoil.eyeslink.mvp.contract.MainContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.MainEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainModel implements MainContract.BaseMainModel {

    @Inject
    public MainModel() {
    }

    @Override
    public List<MainEntity> getList() {
        List<MainEntity> list = new ArrayList<MainEntity>();
        MainEntity entity = new MainEntity();
        entity.setX(10);
        entity.setY("30");
        list.add(entity);
        return list;
    }

    @Override
    public void onDestroy() {
        Log.d("MainModel", "onDestroy");
    }
}
