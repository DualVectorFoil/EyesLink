package com.dualvectorfoil.eyeslink.mvp.contract;

import com.dualvectorfoil.eyeslink.mvp.model.entity.MainEntity;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IModel;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnLoadDataListener;
import com.dualvectorfoil.eyeslink.mvp.view.IMainView;

import java.util.List;

public interface MainContract {

    interface BaseMainView extends IMainView {
        void showToast(String msg);
    }

    interface BaseMainModel extends IModel {
        List<MainEntity> getList();
    }

    interface OnToastShowListner extends OnLoadDataListener<MainEntity> {
    }
}
