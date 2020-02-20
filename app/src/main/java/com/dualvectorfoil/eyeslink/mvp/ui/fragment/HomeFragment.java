package com.dualvectorfoil.eyeslink.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.event.CommonEvent;
import com.dualvectorfoil.eyeslink.di.component.DaggerFrHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.FrHomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.presenter.FrHomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.DragGridAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseFragment;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnConfirmListener;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;
import com.dualvectorfoil.eyeslink.util.DialogUtils;
import com.huxq17.handygridview.HandyGridView;
import com.huxq17.handygridview.listener.OnItemCapturedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HomeFragment extends BaseFragment<FrHomePresenter> implements
        FrHomeContract.IFrHomeView, View.OnTouchListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, OnItemCapturedListener, DragGridAdapter.OnUrlInfoTagDeleteListener {

    private static final String TAG = "FR_HOME_TAG_fragment";

    private Activity mActivity;
    private HandyGridView mLauncherView;
    private DragGridAdapter mDragGridAdapter;

    private AlertDialog mDeleteUrlInfoDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerFrHomeComponent.builder().frHomeModule(new FrHomeModule(this)).build().inject(this);
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initView() {
        mActivity = getActivity();
        mLauncherView = (HandyGridView) mRootView.findViewById(R.id.launcher_view);

        mLauncherView.setAutoOptimize(false);
        mLauncherView.setScrollSpeed(750);
        mLauncherView.setOnItemLongClickListener(this);
        mLauncherView.setOnItemCapturedListener(this);
        mLauncherView.setOnItemClickListener(this);
        mLauncherView.setOnTouchListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<UrlInfo> urlInfoItemViewList = mPresenter.getUrlInfoItemViewList();
        mDragGridAdapter = new DragGridAdapter(mActivity, urlInfoItemViewList);
        mDragGridAdapter.setOnUrlInfoTagDeleteListener(this);
        mLauncherView.setAdapter(mDragGridAdapter);
        setMode(HandyGridView.MODE.LONG_PRESS);
    }

    private void setMode(HandyGridView.MODE mode) {
        mLauncherView.setMode(mode);
        mDragGridAdapter.setInEditMode(mode == HandyGridView.MODE.TOUCH);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mLauncherView.isTouchMode()) {
                    mLauncherView.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mLauncherView.isTouchMode() || mLauncherView.isNoneMode() || mDragGridAdapter.isFixed(position)) {
            return;
        }

        // TODO start webview
        Toast.makeText(mActivity, "点击了" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mLauncherView.isTouchMode() && !mLauncherView.isNoneMode() && !mDragGridAdapter.isFixed(position)) {
            setMode(HandyGridView.MODE.TOUCH);
            mLauncherView.requestDisallowInterceptTouchEvent(true);
            return true;
        }
        return false;
        // TODO 改成弹出长按地址的详细操作，在UrlInfoTagLayout里面处理，addview到windowmanager里
    }

    @Override
    public void onItemCaptured(View v, int position) {
        v.setScaleX(1.2f);
        v.setScaleY(1.2f);
    }

    @Override
    public void onItemReleased(View v, int position) {
        v.setScaleX(1.0f);
        v.setScaleY(1.0f);
    }

    @Override
    public void onDelete(UrlInfoTagLayout tag, DragGridAdapter.OnUrlInfoModelDeleteListener listener) {
        mPresenter.deleteUrlInfo(tag, listener);
    }

    @Override
    public void showDeleteUrlInfoDialog(UrlInfoTagLayout tag, OnConfirmListener listener) {
        mDeleteUrlInfoDialog = DialogUtils.createDeleteUrlInfoDialog(mActivity, tag, listener);
        mDeleteUrlInfoDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitUrlInfoEditMode(CommonEvent event) {
        if (!event.shouldProcess(CommonEvent.ON_EXIT_URL_INFO_EDIT_MODE)) {
            return;
        }

        setMode(HandyGridView.MODE.LONG_PRESS);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnterUrlInfoEditMode(CommonEvent event) {
        if (!event.shouldProcess(CommonEvent.ON_ENTER_URL_INFO_EDIT_MODE)) {
            return;
        }

        if (!mLauncherView.isTouchMode() && !mLauncherView.isNoneMode()) {
            setMode(HandyGridView.MODE.TOUCH);
            mLauncherView.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddUrlInfoSuccess(CommonEvent event) {
        if (!event.shouldProcess(CommonEvent.ON_ADD_URL_INFO_SUCCESS)) {
            return;
        }
        mDragGridAdapter.setData(mPresenter.getUrlInfoItemViewList());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
