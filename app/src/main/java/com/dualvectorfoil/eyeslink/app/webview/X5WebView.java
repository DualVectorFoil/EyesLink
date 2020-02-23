package com.dualvectorfoil.eyeslink.app.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.dualvectorfoil.eyeslink.BuildConfig;
import com.dualvectorfoil.eyeslink.R;
import com.dualvectorfoil.eyeslink.app.jsInterface.ObtainVideoTagJSInterface;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
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

    private void init(Context context) {
        if (mWebView != null) {
            return;
        }

        mWebView = ((Activity) context).findViewById(R.id.main_web_view);

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
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(context.getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

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
        mWebView.addJavascriptInterface(new ObtainVideoTagJSInterface(), "java_obj");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "url: " + url);
                }

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    webView.loadUrl(url);
                    return super.shouldOverrideUrlLoading(webView, url);
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                if (sslErrorHandler != null) {
                    sslErrorHandler.proceed();
                }
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                webView.loadUrl("javascript:window.java_obj.onHtml('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });
        mWebView.setClickable(true);
        mWebView.setOnTouchListener((v, event) -> false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);

        mWebView.clearCache(true);
        mWebView.clearHistory();
    }

    public WebView create(Context context) {
        init(context);
        return mWebView;
    }
}
