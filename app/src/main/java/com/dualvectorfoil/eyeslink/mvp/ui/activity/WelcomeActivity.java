package com.dualvectorfoil.eyeslink.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.constants.Constants;
import com.dualvectorfoil.eyeslink.mvp.ui.base.BaseApplication;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class WelcomeActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseApplication.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_TIME);
        initRealmDB();
    }

    private void initRealmDB() {
        Realm.init(BaseApplication.getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Constants.DB_NAME)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
