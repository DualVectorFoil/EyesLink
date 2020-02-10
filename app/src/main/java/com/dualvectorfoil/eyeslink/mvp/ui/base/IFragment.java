package com.dualvectorfoil.eyeslink.mvp.ui.base;

import android.os.Bundle;

public interface IFragment {

    int getLayout();

    void initView();

    void initData(Bundle savedInstanceState);
}
