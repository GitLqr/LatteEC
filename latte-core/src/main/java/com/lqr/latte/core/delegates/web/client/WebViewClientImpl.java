package com.lqr.latte.core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lqr.latte.core.app.ConfigType;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.web.IPageLoadListener;
import com.lqr.latte.core.delegates.web.WebDelegate;
import com.lqr.latte.core.delegates.web.route.Rounter;
import com.lqr.latte.core.ui.loader.LatteLoader;
import com.lqr.latte.core.util.LattePreference;
import com.lqr.latte.core.util.log.LatteLogger;

/**
 * Created by lqr on 2018/5/1.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE;
    private IPageLoadListener mPageLoadListener = null;
    private final Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading", url);
        return Rounter.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mPageLoadListener != null) {
            mPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mPageLoadListener != null) {
            mPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        }, 1000);
    }

    private void syncCookie() {
        final CookieManager cookieManager = CookieManager.getInstance();
        /*
         注意：这里的cookie和api请求的cookie是不一样的，这个在网页不可见
         */
        final String webHost = Latte.getConfiguration(ConfigType.WEB_HOST);
        if (webHost != null) {
            if (cookieManager.hasCookies()) {
                final String cookieStr = cookieManager.getCookie(webHost);
                if (cookieStr != null && !cookieStr.equals("")) {
                    LattePreference.addCustomAppProfile("cookie", cookieStr);
                }
            }
        }
    }
}
