package com.dualvectorfoil.eyeslink.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dualvectorfoil.eyeslink.R;

public class UrlInfoItemView extends LinearLayout {

    private int mImageViewSize = 30;
    private int mTextViewSize = 10;

    private int mUrlIconId = R.drawable.default_url_icon;
    private CharSequence mUrlName;

    private ImageView mImageView;
    private TextView mTextView;

    public UrlInfoItemView(Context context) {
        super(context);
        init(context);
    }

    public UrlInfoItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UrlInfoItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public UrlInfoItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        mImageView = new ImageView(context);
        mImageView.setImageResource(R.drawable.default_url_icon);
        mTextView = new TextView(context);

        LayoutParams imageViewParams = new LayoutParams(mImageViewSize, mImageViewSize);
        imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;

        LayoutParams textViewParams = new LayoutParams(mTextViewSize, mTextViewSize);
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;

        addView(mImageView, imageViewParams);
        addView(mTextView, textViewParams);
    }

    public void setUrlIconId(int id) {
        mUrlIconId = id;
        mImageView.setImageResource(id);
    }

    public void setUrlName(CharSequence name) {
        mUrlName = name;
        mTextView.setText(name);
    }

    public UrlInfoItemView copySelf() {
        Context context = getContext();
        UrlInfoItemView view = new UrlInfoItemView(context);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(mUrlIconId);
        TextView textView = new TextView(context);
        textView.setText(mUrlName);

        LayoutParams imageViewParams = new LayoutParams(mImageViewSize, mImageViewSize);
        imageViewParams.gravity = Gravity.CENTER_HORIZONTAL;

        LayoutParams textViewParams = new LayoutParams(mTextViewSize, mTextViewSize);
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL;

        view.addView(imageView, imageViewParams);
        view.addView(textView, textViewParams);

        return view;
    }
}
