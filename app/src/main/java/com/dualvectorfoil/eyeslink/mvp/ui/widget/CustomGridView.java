package com.dualvectorfoil.eyeslink.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.huxq17.handygridview.HandyGridView;

public class CustomGridView extends HandyGridView {

    private OnItemLongClickListener mOnItemLongClickListener;

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnItemLongClickListener != null) {
            return mOnItemLongClickListener.onItemLongClick(parent, view, position, id);
        }
        return false;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        super.setOnItemLongClickListener(listener);
    }
}
