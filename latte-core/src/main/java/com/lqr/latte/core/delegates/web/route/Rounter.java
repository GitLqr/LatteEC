package com.lqr.latte.core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.delegates.web.WebDelegate;
import com.lqr.latte.core.delegates.web.WebDelegateImpl;

/**
 * Created by lqr on 2018/5/1.
 */

public class Rounter {
    private Rounter() {

    }

    private static class Holder {
        private static final Rounter INSTANCE = new Rounter();
    }

    public static Rounter getInstance() {
        return Holder.INSTANCE;
    }

    // 控制 shouldOverrideUrlLoading() 的返回值
    public final boolean handleWebUrl(WebDelegate delegate, String url) {

        // 如果是电话协议
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);

        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("WebView is null !");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    /**
     * 跳转到拨号界面
     */
    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }

}
