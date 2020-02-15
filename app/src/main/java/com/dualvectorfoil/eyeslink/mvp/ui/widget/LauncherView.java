package com.dualvectorfoil.eyeslink.mvp.ui.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class LauncherView extends GridView implements AdapterView.OnItemLongClickListener {

    private static final String TAG = "LauncherView";

    private float mDownRawX;
    private float mDownRawY;
    private float mX;
    private float mY;
    private boolean mIsDragging = false;

    private int mCurPos;
    private int mTempPos;
    private UrlInfoItemView mItemView;
    private UrlInfoItemView mDragView;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;

    public LauncherView(Context context) {
        super(context);
        init(context);
    }

    public LauncherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setOnItemLongClickListener(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownRawX = ev.getRawX();
                mDownRawY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (mIsDragging || view.getClass() != UrlInfoItemView.class) {
            return false;
        }

        mItemView = (UrlInfoItemView) view;
        mCurPos = position;
        mTempPos = position;
        mX = mDownRawX - view.getLeft() - this.getLeft();
        mY = mDownRawY - view.getTop() - this.getTop();
        initDragWindow();
        return true;
    }

    private void initDragWindow() {
        Context context = getContext();
        if (mDragView == null) {
            mDragView = mItemView.copySelf();
        }

        if (mParams == null) {
            mParams = new WindowManager.LayoutParams();
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            mParams.format = PixelFormat.RGBA_8888;
            mParams.gravity = Gravity.TOP | Gravity.LEFT;
            mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            mParams.width = mDragView.getLeft() + this.getLeft();
            mParams.height = mDragView.getTop() + this.getTop();
            mItemView.setVisibility(INVISIBLE);
        }

        mWindowManager.addView(mDragView, mParams);
        mIsDragging = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDragging) {
                    updateWindow(ev);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    closeWindow(ev.getX(), ev.getY());
                }
                break;
            default:
        }
        return super.onTouchEvent(ev);
    }

    private void updateWindow(MotionEvent ev) {
        if (!mIsDragging) {
            return;
        }

        float x = ev.getRawX() - mX;
        float y = ev.getRawY() - mY;
        if (mParams != null) {
            mParams.x = (int) x;
            mParams.y = (int) y;
            mWindowManager.updateViewLayout(mDragView, mParams);
        }
        float evX = ev.getX();
        float evY = ev.getY();
        int drapPos = pointToPosition((int) evX, (int) evY);
        Log.i(TAG, "drapPos: " + drapPos + " , tempPosition: " + mTempPos);
        if (drapPos == mTempPos || drapPos == GridView.INVALID_POSITION) {
            return;
        }
        itemMove(drapPos);
    }

    private void itemMove(int drapPos) {
        // TODO 切换item时做切换动画

    }

    private void closeWindow(float x, float y) {

    }
}
