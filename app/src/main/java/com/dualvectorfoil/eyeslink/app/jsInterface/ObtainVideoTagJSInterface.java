package com.dualvectorfoil.eyeslink.app.jsInterface;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.dualvectorfoil.eyeslink.BuildConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ObtainVideoTagJSInterface {

    private static final String TAG = "ObtainVideoTagJSInterface";

    @JavascriptInterface
    public void onHtml(String html) {
        if (BuildConfig.DEBUG) {
            Log.d("nimabi", "" + html);
        }

        Document document = Jsoup.parseBodyFragment(html);
        if (document != null) {
            Log.d(TAG, "onHtml: 解析document成功");
            Elements elements = document.select(".video");
            if (elements != null && !elements.isEmpty()) {
                Element element = elements.get(0);
                String src = element.attr("src");
                Log.d("kengdiea", "src: " + src);
            }
        }
    }
}
