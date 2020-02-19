package com.dualvectorfoil.eyeslink.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.mvp.model.entity.UrlInfo;
import com.dualvectorfoil.eyeslink.mvp.ui.base.OnConfirmListener;
import com.dualvectorfoil.eyeslink.mvp.ui.widget.UrlInfoTagLayout;

public class DialogUtils {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);
        TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
        if (msg != null && !msg.equals("")) {
            tipTextView.setText(msg);// 设置加载信息
        }

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(false);//
        loadingDialog.setContentView(v);// 设置布局

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.3);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (loadingDialog.getWindow() != null) {
            loadingDialog.getWindow().setLayout(width, height);
        }

        return loadingDialog;
    }

    public static AlertDialog createAddUrlInfoDialog(Context context, View.OnClickListener positiveListener, View dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("新增地址")
                .setView(dialogView)
                .setPositiveButton("确认", null)
                .setNegativeButton("取消", (DialogInterface dialogInterface, int i) -> {})
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener((DialogInterface) -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(positiveListener));
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static AlertDialog createDeleteUrlInfoDialog(Context context, UrlInfoTagLayout view, OnConfirmListener listener) {
        UrlInfo urlInfo = view.getUrlInfo();
        View v = null;
        if (urlInfo != null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.deleteurlinfo_dialog, null);

            TextView tv = v.findViewById(R.id.launcher_item_text);
            tv.setText(urlInfo.getname());

            ImageView imgV = v.findViewById(R.id.launcher_item_image);
            int resId = urlInfo.getResId();
            if (resId == -1) {
                imgV.setImageResource(R.drawable.default_url_icon);
            } else {
                imgV.setImageResource(resId);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除: " + view.getUrlInfo().geturl())
                .setPositiveButton("确认", (DialogInterface dialog, int which) -> listener.onConfirmed())
                .setNegativeButton("取消", (DialogInterface dialog, int which) -> listener.onDenied())
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener((DialogInterface) -> listener.onDenied());

        if (v != null) {
            dialog.setView(v);
        }

        return dialog;
    }
}
