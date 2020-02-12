package com.dualvectorfoil.eyeslink.mvp.ui.widget;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class AddUrlInfoView extends LinearLayout {

    private static final String TAG = "AddUrlInfoView";

    private EditText mUrlEdit;
    private EditText mNameEdit;
    private EditText mUserEdit;
    private EditText mPasswordEdit;

    public AddUrlInfoView(Context context) {
        super(context);
        init(context);
    }

    public AddUrlInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddUrlInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AddUrlInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mUrlEdit = new EditText(context);
        mUrlEdit.setHint("请输入网站地址");
        mUrlEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        mNameEdit = new EditText(context);
        mNameEdit.setHint("(可选)请输入网站别名");
        mNameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        mUserEdit = new EditText(context);
        mUserEdit.setHint("(可选)请输入网站用户名");
        mUserEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        mPasswordEdit = new EditText(context);
        mPasswordEdit.setHint("(可选)请输入网站密码");
        mPasswordEdit.setInputType(InputType.TYPE_MASK_CLASS);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 0);
        addView(mUrlEdit, params);
        addView(mNameEdit, params);
        addView(mUserEdit, params);
        addView(mPasswordEdit, params);
    }

    public String getUrl() {
        if (mUrlEdit == null) {
            return "";
        }
        return mUrlEdit.getText().toString();
    }

    public String getName() {
        if (mNameEdit == null) {
            return "";
        }
        return mNameEdit.getText().toString();
    }

    public String getUser() {
        if (mNameEdit == null) {
            return "";
        }
        return mNameEdit.getText().toString();
    }

    public String getPassword() {
        if (mPasswordEdit == null) {
            return "";
        }
        return mPasswordEdit.getText().toString();
    }
}
