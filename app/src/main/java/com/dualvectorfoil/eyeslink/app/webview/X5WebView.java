package com.dualvectorfoil.eyeslink.app.webview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView {

    private static final String TAG = "X5WebView";

    private static final String APP_CACHE_DIRNAME = "webCache";

    private WebView mWebView;

    private static volatile X5WebView sInstance;

    public static X5WebView getInstance() {
        if (sInstance == null) {
            synchronized (X5WebView.class) {
                if (sInstance == null) {
                    sInstance = new X5WebView();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        if (mWebView != null) {
            return;
        }

        mWebView = new WebView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setSupportMultipleWindows(true);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(context.getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(webView, request);
            }
        });
        mWebView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        mWebView.setBackgroundResource(android.R.color.white);
        // TODO processBar
//        mWebView.setWebChromeClient(new WebChromeClient() {
//
//        });
        // TODO download listener
//        mWebView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
//
//            }
//        });
        mWebView.setClickable(true);
        mWebView.setOnTouchListener((v, event) -> false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void removeWebView() {
        if (mWebView == null) {
            return;
        }

        ViewGroup parent = (ViewGroup) mWebView.getParent();
        if (parent != null) {
            parent.removeView(mWebView);
        }

        mWebView.clearCache(true);
        mWebView.clearHistory();
    }
}
