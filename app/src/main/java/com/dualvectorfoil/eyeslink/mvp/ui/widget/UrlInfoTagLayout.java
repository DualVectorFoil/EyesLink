package com.dualvectorfoil.eyeslink.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.util.DensityUtil;

public class UrlInfoTagLayout extends RelativeLayout {

    private Drawable mDelIcon;
    private int mIconWidth;
    private int mIconHeight;

    private boolean mShowDelIcon = false;
    private Rect mDelRect;
    private Rect mAssumeDelRect;

    private OnTagDeleteListener mListener;

    public UrlInfoTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UrlInfoTagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public UrlInfoTagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public UrlInfoTagLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mDelIcon = context.getResources().getDrawable(R.mipmap.ic_url_info_tag_delete_icon);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mAssumeDelRect == null) {
            setDeleteBounds();
        }
        if (mShowDelIcon) {
            mDelIcon.draw(canvas);
        }
    }

    private void setDeleteBounds() {
        mIconWidth = mDelIcon.getIntrinsicWidth();
        mIconHeight = mDelIcon.getIntrinsicHeight();
        int left = getWidth() - mIconWidth;
        int top = 0;
        mDelRect = new Rect(left, top, left + mIconWidth, top + mIconHeight);
        int padding = DensityUtil.dip2px(getContext(), 10);
        mAssumeDelRect = new Rect(mDelRect.left, mDelRect.top, mDelRect.left + mIconWidth + padding, mDelRect.top + mIconHeight + padding);
        mDelIcon.setBounds(mDelRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean contains = mAssumeDelRect.contains(x, y);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (contains && mShowDelIcon) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (contains && mShowDelIcon) {
                    if (mListener != null) {
                        mListener.onDelete(this);
                    }
                    return true;
                }
                break;
            default:
        }

        return super.onTouchEvent(event);
    }

    public void showDeleteIcon(boolean show) {
        mShowDelIcon = show;
        invalidate();
    }

    public void setOnTagDeleteListener(OnTagDeleteListener listener) {
        mListener = listener;
    }

    public interface OnTagDeleteListener {

        void onDelete(View deleteView);
    }
}
