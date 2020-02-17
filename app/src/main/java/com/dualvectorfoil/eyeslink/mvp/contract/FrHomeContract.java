package com.dualvectorfoil.eyeslink.mvp.contract;

import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IModel;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IView;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnLoadDataListener;

import java.util.List;

public interface FrHomeContract {

    interface IFrHomeView extends IView {

    }

    interface IFrHomeModel extends IModel {

        List<UrlInfo> getUrlInfoItemViewList();
    }

    // TODO
    interface OnDataLoadListener extends OnLoadDataListener<Object> {

    }
}
