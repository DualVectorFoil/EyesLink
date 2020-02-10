package com.dualvectorfoil.eyeslink.mvp.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.util.DialogUtils;
import com.squareup.leakcanary.RefWatcher;
import com.trello.rxlifecycle2.components.support.RxFragment;

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment implements IView, IFragment {

    protected P mPresenter;

    protected View mRootView;
    protected Dialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), null);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        if (mPresenter == null) {
            mPresenter = createPresenter();
        }

        mDialog = DialogUtils.createLoadingDialog(getActivity(), "请稍后...");

        initView();
        initData(savedInstanceState);
        return mRootView;
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Context context = getActivity();
        if (context == null) {
            Log.e("RefWatcherErr", "Activity of fragment is null, init RefWatcher failed.");
            return;
        }
        RefWatcher refWatcher = BaseApplication.getRefWatcher(context);
        refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
