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

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.di.component.DaggerFrHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.FrHomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.presenter.FrHomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.DragGridAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseFragment;
import com.huxq17.handygridview.HandyGridView;
import com.huxq17.handygridview.listener.OnItemCapturedListener;

import java.util.List;

public class HomeFragment extends BaseFragment<FrHomePresenter> implements FrHomeContract.IFrHomeView, View.OnTouchListener, AdapterView.OnItemClickListener {

    private static final String TAG = "FR_HOME_TAG_fragment";

    private Activity mActivity;
    private HandyGridView mLauncherView;
    private DragGridAdapter mDragGridAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DaggerFrHomeComponent.builder().frHomeModule(new FrHomeModule(this)).build().inject(this);
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
        mLauncherView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mLauncherView.isTouchMode() && !mLauncherView.isNoneMode() && !mDragGridAdapter.isFixed(position)) {
                    setMode(HandyGridView.MODE.TOUCH);
                    return true;
                }
                return false;
            }
        });
        mLauncherView.setOnItemCapturedListener(new OnItemCapturedListener() {
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
        });
        mLauncherView.setOnItemClickListener(this);
        mLauncherView.setOnTouchListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<UrlInfo> urlInfoItemViewList = mPresenter.getUrlInfoItemViewList();
        mDragGridAdapter = new DragGridAdapter(mActivity, urlInfoItemViewList);
        mDragGridAdapter.setOnUrlInfoTagDeleteListener(new DragGridAdapter.OnUrlInfoTagDeleteListener() {
            @Override
            public boolean onDelete(UrlInfo urlInfo) {
                // TODO update realm db, show dialog for remind delete
                return true;
            }
        });
        mLauncherView.setAdapter(mDragGridAdapter);
        setMode(HandyGridView.MODE.LONG_PRESS);
    }

    private void setMode(HandyGridView.MODE mode) {
        mLauncherView.setMode(mode);
        mDragGridAdapter.setInEditMode(mode == HandyGridView.MODE.TOUCH);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("xiha222", "onTouch: " + event.getAction());
        // TODO cancel touch mode
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("xiha222", "onTouch: " + position);
        if (mLauncherView.isTouchMode() || mLauncherView.isNoneMode() || mDragGridAdapter.isFixed(position)) {
            return;
        }

        // TODO start webview
        Toast.makeText(mActivity, "点击了" + position, Toast.LENGTH_SHORT).show();
    }
}
