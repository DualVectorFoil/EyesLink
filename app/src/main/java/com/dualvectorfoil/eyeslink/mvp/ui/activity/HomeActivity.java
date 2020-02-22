package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.app.SearchManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.event.CommonEvent;
import com.dualvectorfoil.eyeslink.app.event.SearchEvent;
import com.dualvectorfoil.eyeslink.di.component.DaggerHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.HomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.HomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.SectionsPagerAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;
import com.dualvectorfoil.eyeslink.mvp.ui.fragment.HomeFragment;
import com.dualvectorfoil.eyeslink.util.DialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.IHomeView {

    private static final String TAG = "HOME_TAG_activity";

    private Toolbar mToolbar;
    private MenuItem mMenuSearchItem;
    private BottomNavigationBar mNaviBar;
    private ViewPager mViewPager;
    private AlertDialog mAddUrlInfoDialog;
    private LayoutInflater mInflater;

    private View mCancelEditModeView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.actiivty_home;
    }

    @Override
    public void initView() {
        // init toolbar
        mInflater = LayoutInflater.from(this);
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mToolbar.inflateMenu(R.menu.home_toolbar_menu);
        initToolbarMenu(mToolbar.getMenu());
        mToolbar.setOnMenuItemClickListener((MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.add_url_item:
                    showAddUrlInfoDialog();
                    break;
                case R.id.edit_url_item:
                    editUrlInfoItem();
                    break;
                default:
            }
            return true;
        });

        // init viewpager
        mViewPager = findViewById(R.id.sections_view_pager);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new HomeFragment());
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mNaviBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(0);

        // init bottom navigation bar
        mNaviBar = (BottomNavigationBar) findViewById(R.id.home_navi_bar);
        mNaviBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mNaviBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        mNaviBar.addItem(new BottomNavigationItem(R.mipmap.ic_home_page_navi_icon, "首页"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_message_icon, "消息"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_setting_icon, "设置"))
                .initialise();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    private void showAddUrlInfoDialog() {
        if (mAddUrlInfoDialog == null) {
            View addUrlInfoView = mInflater.inflate(R.layout.addurlinfo_dialog, null);
            EditText urlEtView = (EditText) addUrlInfoView.findViewById(R.id.url_edit_text);
            EditText nameEtView = (EditText) addUrlInfoView.findViewById(R.id.name_edit_text);
            EditText userEtView = (EditText) addUrlInfoView.findViewById(R.id.user_edit_text);
            EditText passwordEtView = (EditText) addUrlInfoView.findViewById(R.id.password_edit_text);

            mAddUrlInfoDialog = DialogUtils.createAddUrlInfoDialog(this,
                    (View v) -> {
                        boolean isSuccess = mPresenter.handleAddUrlInfo(
                                urlEtView.getText().toString(),
                                nameEtView.getText().toString(),
                                userEtView.getText().toString(),
                                passwordEtView.getText().toString()
                        );
                        if (isSuccess) {
                            urlEtView.setText("");
                            urlEtView.requestFocus();
                            nameEtView.setText("");
                            userEtView.setText("");
                            passwordEtView.setText("");
                            mAddUrlInfoDialog.dismiss();
                        }
                    }, addUrlInfoView, "新增地址");
        }
        mAddUrlInfoDialog.show();
    }

    private void searchUrlInfoItem() {

    }

    private void editUrlInfoItem() {
        Toast.makeText(this, "拖动网站图标进行编辑", Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommonEvent(CommonEvent.ON_ENTER_URL_INFO_EDIT_MODE));

        if (mWindowManager == null) {
            mWindowManager = (WindowManager) getSystemService(WindowManager.class);
            if (mWindowManager == null) {
                throw new IllegalStateException("get WindowManager failed");
            }
        }

        if (mCancelEditModeView == null || mParams == null) {
            mCancelEditModeView = mInflater.inflate(R.layout.confirm_edit_button, null);
            ((ImageButton) mCancelEditModeView.findViewById(R.id.confirm_edit_btn)).setOnClickListener((View) -> {
                EventBus.getDefault().post(new CommonEvent(CommonEvent.ON_EXIT_URL_INFO_EDIT_MODE));
                mWindowManager.removeView(mCancelEditModeView);
            });

            mParams = new WindowManager.LayoutParams();
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.gravity = Gravity.END | Gravity.TOP;
            mParams.x = 0;
            mParams.y = 0;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        mWindowManager.addView(mCancelEditModeView, mParams);
    }

    @Override
    public void onAddUrlInfoSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new CommonEvent(CommonEvent.ON_ADD_URL_INFO_SUCCESS));
    }

    private void initToolbarMenu(Menu menu) {
        if (menu == null || !menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
            return;
        }

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (mMenuSearchItem == null) {
            mMenuSearchItem = menu.findItem(R.id.search_url_item);
        }
        SearchView searchView = (SearchView) mMenuSearchItem.getActionView();
        searchView.setQueryHint("请输入搜索网站");
        searchView.setIconified(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                EventBus.getDefault().post(new SearchEvent(SearchEvent.SEARCH_URL_INFO_ITEM, newText));
                return false;
            }
        });
        menu.findItem(R.id.search_url_item).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                mNaviBar.setVisibility(View.GONE);
                menu.setGroupVisible(0, false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mNaviBar.setVisibility(View.VISIBLE);
                menu.setGroupVisible(0, true);
                return true;
            }
        });

        try {
            Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(menu, true);
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }

    @Override
    public void onBackPressed() {
        if (mMenuSearchItem.isActionViewExpanded()) {
            mMenuSearchItem.collapseActionView();
            return;
        }

        moveTaskToBack(true);
    }
}
