package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.di.component.DaggerHomeComponent;
import com.dualvectorfoil.eyeslink.di.module.HomeModule;
import com.dualvectorfoil.eyeslink.mvp.contract.HomeContract;
import com.dualvectorfoil.eyeslink.mvp.presenter.HomePresenter;
import com.dualvectorfoil.eyeslink.mvp.ui.adapter.SectionsPagerAdapter;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseActivity;
import com.dualvectorfoil.eyeslink.mvp.ui.fragment.HomeFragment;
import com.dualvectorfoil.eyeslink.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.IHomeView {

    private static final String TAG = "HOME_TAG_activity";

    private Toolbar mToolbar;
    private BottomNavigationBar mNaviBar;
    private ViewPager mViewPager;
    private AlertDialog mAddUrlInfoDialog;

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
        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mToolbar.inflateMenu(R.menu.home_toolbar_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_url_item:
                        showAddUrlInfoDialog();
                        break;
                    default:
                }
                return true;
            }
        });

        // init viewpager
        mViewPager = findViewById(R.id.sections_view_pager);
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
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

    public void showAddUrlInfoDialog() {
        if (mAddUrlInfoDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View addUrlInfoView = inflater.inflate(R.layout.addurlinfo_dialog, null);
            EditText urlEtView = (EditText) addUrlInfoView.findViewById(R.id.url_edit_text);
            EditText nameEtView = (EditText) addUrlInfoView.findViewById(R.id.name_edit_text);
            EditText userEtView = (EditText) addUrlInfoView.findViewById(R.id.user_edit_text);
            EditText passwordEtView = (EditText) addUrlInfoView.findViewById(R.id.password_edit_text);

            mAddUrlInfoDialog = DialogUtils.createAddUrlInfoDialog(this,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean isSuccess = mPresenter.handleAddUrlInfo(
                                    ((EditText) addUrlInfoView.findViewById(R.id.url_edit_text)).getText().toString(),
                                    ((EditText) addUrlInfoView.findViewById(R.id.name_edit_text)).getText().toString(),
                                    ((EditText) addUrlInfoView.findViewById(R.id.user_edit_text)).getText().toString(),
                                    ((EditText) addUrlInfoView.findViewById(R.id.password_edit_text)).getText().toString()
                            );
                            if (isSuccess) {
                                urlEtView.setText("");
                                nameEtView.setText("");
                                userEtView.setText("");
                                passwordEtView.setText("");
                                mAddUrlInfoDialog.dismiss();
                            }
                        }
                    }, addUrlInfoView);
        }
        mAddUrlInfoDialog.show();
    }

    @Override
    public void showAddUrlInfoToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
