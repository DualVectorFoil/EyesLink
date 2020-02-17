package com.dualvectorfoil.eyeslink.mvp.contract;

import com.dualvectorfoil.eyeslink.mvp.ui.base.IModel;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IView;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnLoadDataListener;

public interface HomeContract {

    interface IHomeView extends IView {

        void showAddUrlInfoToast(String msg);
    }

    interface IHomeModel extends IModel {

        boolean addUrlInfo(String url, String name, String user, String password);
    }

    // TODO
    interface OnLoadListener extends OnLoadDataListener<Object> {

    }
}
