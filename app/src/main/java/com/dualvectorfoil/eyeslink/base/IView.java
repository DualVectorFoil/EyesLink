package com.dualvectorfoil.eyeslink.base;

import android.app.Dialog;

public interface IView {

    Dialog getLocalDialog();

    void cancelLoadDialog();
}
