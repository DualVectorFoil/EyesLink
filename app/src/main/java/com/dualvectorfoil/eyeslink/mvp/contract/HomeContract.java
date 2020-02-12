package com.dualvectorfoil.eyeslink.mvp.contract;

import com.dualvectorfoil.eyeslink.mvp.ui.base.IModel;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IView;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnLoadDataListener;

public interface HomeContract {

    interface IHomeView extends IView {

        // TODO parameters of url info for gridview
        void addUrlIcon();
    }

    interface IHomeModel extends IModel {

        void addUrlInfo();
    }

    // TODO
    interface OnLoadListener extends OnLoadDataListener<Object> {

    }
}
