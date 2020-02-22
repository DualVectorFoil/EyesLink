package com.dualvectorfoil.eyeslink.mvp.ui.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dualvectorfoil.eyeslink.BuildConfig;
import com.dualvectorfoil.eyeslink.app.constants.Constants;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.QbSdk;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    private static Context mContext;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mRefWatcher = setupLeakCanary();
        String processName = getProcessName();
        if (processName.equals(Constants.MAIN_PROCESS_NAME)) {
            initMainProcess();
        } else {
            initWebviewProcess();
        }
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

    private void initMainProcess() {
    }

    private void initWebviewProcess() {
        initX5();
        initRealm();
    }

    private void initX5() {
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean isSuccess) {
                if (!isSuccess) {
                    Log.e(TAG, "initX5 failed");
                }
            }
            @Override
            public void onCoreInitFinished() {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "initX5 finished");
                }
            }
        });
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Constants.DB_NAME)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    private String obtainProcessName(Context cxt) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            Log.e("obtainProcessName", "am is null");
            return "";
        }
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }
}
