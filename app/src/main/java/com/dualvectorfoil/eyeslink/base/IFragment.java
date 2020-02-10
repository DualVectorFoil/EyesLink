package com.dualvectorfoil.eyeslink.base;

import android.os.Bundle;

public interface IFragment {

    int getLayout();

    void initView();

    void initData(Bundle savedInstanceState);
}
