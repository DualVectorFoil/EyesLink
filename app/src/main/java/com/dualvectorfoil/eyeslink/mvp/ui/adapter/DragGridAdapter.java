package com.dualvectorfoil.eyeslink.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;
import com.huxq17.handygridview.scrollrunner.OnItemMovedListener;

import java.util.List;

public class DragGridAdapter extends BaseAdapter implements OnItemMovedListener, UrlInfoTagLayout.OnTagDeleteListener {

    private static final String TAG = "DragGridAdapter";

    private List<UrlInfo> mList;
    private LayoutInflater mInflater;

    private GridView mGridView;
    private boolean mInEditMode = false;

    private OnUrlInfoTagDeleteListener mDeleteTagListener;
    private OnChangeUrlInfoItemIndexListener mChangeIndexListener;

    public DragGridAdapter(Context context, List<UrlInfo> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<UrlInfo> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setInEditMode(boolean inEditMode) {
        mInEditMode = inEditMode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mGridView == null) {
            mGridView = (GridView) parent;
        }

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.launcher_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.launcher_item_text);
            holder.imgV = (ImageView) convertView.findViewById(R.id.launcher_item_image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.tv.setText(mList.get(position).getname());
        int imgResId = mList.get(position).getResId();
        if (imgResId == -1) {
            holder.imgV.setImageResource(R.drawable.default_url_icon);
            holder.imgV.setTag(R.drawable.default_url_icon);
        } else {
            holder.imgV.setImageResource(imgResId);
            holder.imgV.setTag(imgResId);
        }

        UrlInfoTagLayout tag = (UrlInfoTagLayout) convertView;
        tag.setUrlInfo(mList.get(position));
        if (!isFixed(position)) {
            tag.showDeleteIcon(mInEditMode);
        } else {
            tag.showDeleteIcon(false);
        }
        tag.setOnTagDeleteListener(this);
        if (mChangeIndexListener != null) {
            mChangeIndexListener.onChangeIndex(tag.getUrlInfo(), position);
        }

        return convertView;
    }

    @Override
    public void onItemMoved(int from, int to) {
        UrlInfo urlInfo = mList.remove(from);
        mList.add(to, urlInfo);
    }

    @Override
    public boolean isFixed(int position) {
        return false;
    }

    @Override
    public void onDelete(View deleteView) {
        if (mDeleteTagListener == null) {
            Log.w(TAG, "mDeleteTagListener should not be null, use setOnUrlInfoTagDeleteListener to set");
            return;
        }

        int index = mGridView.indexOfChild(deleteView);
        if (index < 0) {
            return;
        }
        int position = index + mGridView.getFirstVisiblePosition();

        mDeleteTagListener.onDelete((UrlInfoTagLayout) deleteView, new OnUrlInfoModelDeleteListener() {
            @Override
            public void onDelete() {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public void setOnUrlInfoTagDeleteListener(OnUrlInfoTagDeleteListener listener) {
        mDeleteTagListener = listener;
    }

    public void setOnChangeUrlInfoItemIndex(OnChangeUrlInfoItemIndexListener listener) {
        mChangeIndexListener = listener;
    }

    private class Holder {

        TextView tv = null;
        ImageView imgV = null;

        public TextView getTextView() {
            return tv;
        }

        public void setTextView(TextView tv) {
            this.tv = tv;
        }

        public ImageView getImageView() {
            return imgV;
        }

        public void setImageView(ImageView imgV) {
            this.imgV = imgV;
        }
    }

    public interface OnUrlInfoTagDeleteListener {

        void onDelete(UrlInfoTagLayout tag, OnUrlInfoModelDeleteListener listener);
    }

    public interface OnUrlInfoModelDeleteListener {

        void onDelete();
    }

    public interface OnChangeUrlInfoItemIndexListener {

        void onChangeIndex(UrlInfo urlInfo, int newIndex);
    }
}
