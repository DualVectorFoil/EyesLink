package com.dualvectorfoil.eyeslink.mvp.ui.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;

public class BaseApplication extends Application {

    private static Context mContext;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mRefWatcher = setupLeakCanary();
        initX5();
    }

    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d("X5_kernel", "onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean isFinished) {
                Log.d("X5_kernel", "onViewInitFinished is " + isFinished);
            }
        };

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static synchronized Context getContext() {
        return mContext;
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication leakApplication = (BaseApplication) context.getApplicationContext();
        return leakApplication.mRefWatcher;
    }
}
