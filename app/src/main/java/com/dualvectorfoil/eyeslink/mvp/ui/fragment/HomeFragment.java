package com.dualvectorfoil.eyeslink.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.constants.Constants;
import com.dualvectorfoil.eyeslink.app.event.CommonEvent;
import com.dualvectorfoil.eyeslink.app.event.SearchEvent;
import com.dualvectorfoil.eyeslink.di.component.DaggerFrHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.FrHomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.FrHomeContract;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.presenter.FrHomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.activity.WebActivity;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.DragGridAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseFragment;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnConfirmListener;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.CustomGridView;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;
import com.dualvectorfoil.eyeslink.util.DialogUtils;
import com.huxq17.handygridview.listener.OnItemCapturedListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class HomeFragment extends BaseFragment<FrHomePresenter> implements
        FrHomeContract.IFrHomeView, View.OnTouchListener, AdapterView.OnItemClickListener, OnItemCapturedListener,
        DragGridAdapter.OnUrlInfoTagDeleteListener, DragGridAdapter.OnChangeUrlInfoItemIndexListener {

    private static final String TAG = "FR_HOME_TAG_fragment";

    private LayoutInflater mInflater;
    private Activity mActivity;
    private CustomGridView mLauncherView;
    private DragGridAdapter mDragGridAdapter;

    private AlertDialog mEditUrlInfoDialog;
    private EditText mNameEt;
    private EditText mUserEt;
    private EditText mPasswordEt;
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
        mLauncherView = (CustomGridView) mRootView.findViewById(R.id.launcher_view);

        mLauncherView.setAutoOptimize(false);
        mLauncherView.setScrollSpeed(750);
        mLauncherView.setOnItemCapturedListener(this);
        mLauncherView.setOnItemClickListener(this);
        mLauncherView.setOnTouchListener(this);

        registerForContextMenu(mLauncherView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<UrlInfo> urlInfoItemViewList = mPresenter.getUrlInfoItemViewList();
        mDragGridAdapter = new DragGridAdapter(mActivity, urlInfoItemViewList);
        mDragGridAdapter.setOnUrlInfoTagDeleteListener(this);
        mDragGridAdapter.setOnChangeUrlInfoItemIndex(this);
        mLauncherView.setAdapter(mDragGridAdapter);
        setMode(CustomGridView.MODE.LONG_PRESS);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        mActivity.getMenuInflater().inflate(R.menu.url_info_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        UrlInfo urlInfo = (UrlInfo) mDragGridAdapter.getItem(itemInfo.position);
        // TODO
        switch (itemId) {
            case R.id.open_item:
                openUrlInfoItem(urlInfo);
                break;
            case R.id.invite_item:
                break;
            case R.id.edit_item:
                editUrlInfoItem(urlInfo);
                break;
            case R.id.delete_item:
                if (mPresenter.deleteUrlInfo(urlInfo)) {
                    mDragGridAdapter.setData(mPresenter.getUrlInfoItemViewList());
                }
                break;
            default:
        }

        return super.onContextItemSelected(item);
    }

    private void setMode(CustomGridView.MODE mode) {
        mLauncherView.setMode(mode);
        mDragGridAdapter.setInEditMode(mode == CustomGridView.MODE.TOUCH);
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

        UrlInfoTagLayout tag = (UrlInfoTagLayout) view;
        if (tag.getUrlInfo() == null) {
            Log.e(TAG, "UrlInfoTagLayout has not UrlInfo");
            return;
        }
        openUrlInfoItem(tag.getUrlInfo());
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

    @Override
    public void onChangeIndex(UrlInfo urlInfo, int newIndex) {
        mPresenter.onChangeUrlInfoItemIndex(urlInfo, newIndex);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitUrlInfoEditMode(CommonEvent event) {
        if (!event.shouldProcess(CommonEvent.ON_EXIT_URL_INFO_EDIT_MODE)) {
            return;
        }

        setMode(CustomGridView.MODE.LONG_PRESS);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnterUrlInfoEditMode(CommonEvent event) {
        if (!event.shouldProcess(CommonEvent.ON_ENTER_URL_INFO_EDIT_MODE)) {
            return;
        }

        if (!mLauncherView.isTouchMode() && !mLauncherView.isNoneMode()) {
            setMode(CustomGridView.MODE.TOUCH);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchUrlInfoItem(SearchEvent event) {
        if (!event.shouldProcess(SearchEvent.SEARCH_URL_INFO_ITEM)) {
            return;
        }
        mDragGridAdapter.setData(mPresenter.getUrlInfoItemViewList(event.getSearchInfo()));
    }

    private void openUrlInfoItem(UrlInfo urlInfo) {
        if (urlInfo == null) {
            Log.d(TAG, "urlInfo is null, openUrlInfoItem failed");
            return;
        }

        Intent intent = new Intent(mActivity, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", urlInfo.geturl());
        bundle.putString("name", urlInfo.getname());
        bundle.putString("user", urlInfo.getuser());
        bundle.putString("password", urlInfo.getpassword());
        intent.putExtra(Constants.WEB_ACTIVITY_BUNDLE_KEY, bundle);
        startActivity(intent);
    }

    private void editUrlInfoItem(UrlInfo urlInfo) {
        if (mEditUrlInfoDialog == null || mNameEt == null || mUserEt == null || mPasswordEt == null) {
            if (mInflater == null) {
                mInflater = LayoutInflater.from(mActivity);
            }

            View addUrlInfoView = mInflater.inflate(R.layout.editurlinfo_dialog, null);
            mNameEt = (EditText) addUrlInfoView.findViewById(R.id.name_edit_text);
            mNameEt.setText(urlInfo.getname());
            mUserEt = (EditText) addUrlInfoView.findViewById(R.id.user_edit_text);
            mUserEt.setText(urlInfo.getuser());
            mPasswordEt = (EditText) addUrlInfoView.findViewById(R.id.password_edit_text);
            mPasswordEt.setText(urlInfo.getpassword());

            mEditUrlInfoDialog = DialogUtils.createAddUrlInfoDialog(mActivity,
                    (View v) -> {
                        mPresenter.handleUrlInfoItemEdit(urlInfo, mNameEt.getText().toString(), mUserEt.getText().toString(), mPasswordEt.getText().toString());
                        mEditUrlInfoDialog.dismiss();
                    }, addUrlInfoView, "新增地址");
        }

        mNameEt.setText(urlInfo.getname());
        mNameEt.requestFocus();
        mUserEt.setText(urlInfo.getuser());
        mPasswordEt.setText(urlInfo.getpassword());

        mEditUrlInfoDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
