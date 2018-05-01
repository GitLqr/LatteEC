package com.lqr.latteec.example.event;

import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import com.lqr.latte.core.delegates.web.event.Event;

/**
 * Created by lqr on 2018/5/1.
 */

public class TestEvent extends Event {

    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webView.evaluateJavascript("nativeCall();", null);
                    }
                }
            });
        }
        return null;
    }
}
