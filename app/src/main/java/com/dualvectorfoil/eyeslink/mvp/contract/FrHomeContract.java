package com.dualvectorfoil.eyeslink.mvp.contract;

import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IModel;
import com.dualvectorfoil.eyeslink.mvp.ui.base.IView;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnConfirmListener;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;

import java.util.List;

public interface FrHomeContract {

    interface IFrHomeView extends IView {

        void showDeleteUrlInfoDialog(UrlInfoTagLayout tag, OnConfirmListener listener);
    }

    interface IFrHomeModel extends IModel {

        List<UrlInfo> getUrlInfoItemViewList();

        List<UrlInfo> getUrlInfoItemViewList(String searchInfo);

        boolean deleteUrlInfo(UrlInfo urlInfo);

        void onChangeUrlInfoItemIndex(UrlInfo urlInfo, int newIndex);

        void handleUrlInfoItemEdit(UrlInfo urlInfo, String name, String user, String password);
    }
}
