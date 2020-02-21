package com.dualvectorfoil.eyeslink.mvp.presenter;

import android.util.Log;

import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.DragGridAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BasePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnConfirmListener;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;

import java.util.List;

import javax.inject.Inject;

public class FrHomePresenter extends BasePresenter<FrHomeContract.IFrHomeModel, FrHomeContract.IFrHomeView> {

    private static final String TAG = "FR_HOME_TAG_presenter";

    @Inject
    public FrHomePresenter(FrHomeContract.IFrHomeModel model, FrHomeContract.IFrHomeView view) {
        super(model, view);
    }

    public List<UrlInfo> getUrlInfoItemViewList() {
        return mModel.getUrlInfoItemViewList();
    }

    public List<UrlInfo> getUrlInfoItemViewList(String searchInfo) {
        if ("".equals(searchInfo)) {
            return getUrlInfoItemViewList();
        }
        return mModel.getUrlInfoItemViewList(searchInfo);
    }

    public void onChangeUrlInfoItemIndex(UrlInfo urlInfo, int newIndex) {
        mModel.onChangeUrlInfoItemIndex(urlInfo, newIndex);
    }

    public void deleteUrlInfo(UrlInfoTagLayout tag, DragGridAdapter.OnUrlInfoModelDeleteListener delListener) {
        if (tag.getUrlInfo() == null) {
            Log.w(TAG, "UrlInfoTagLayout has no UrlInfo");
        }
        mView.showDeleteUrlInfoDialog(tag, new OnConfirmListener() {
            @Override
            public void onConfirmed() {
                if (mModel.deleteUrlInfo(tag.getUrlInfo())) {
                    delListener.onDelete();
                }
            }

            @Override
            public void onDenied() {
                Log.d(TAG, "Delete url info denied in dialog");
            }
        });
    }

    public void handleUrlInfoItemEdit(UrlInfo urlInfo, String name, String user, String password) {
        mModel.handleUrlInfoItemEdit(urlInfo, name, user, password);
    }

    public boolean deleteUrlInfo(UrlInfo urlInfo) {
        return mModel.deleteUrlInfo(urlInfo);
    }
}
