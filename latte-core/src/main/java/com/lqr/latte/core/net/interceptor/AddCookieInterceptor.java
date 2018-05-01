package com.lqr.latte.core.net.interceptor;

import com.lqr.latte.core.util.LattePreference;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者：CSDN_LQR
 * 描述：添加Cookie的拦截器
 */
public final class AddCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();

        // -------------------------- 普通方式 --------------------------
        String cookie = LattePreference.getCustomAppProfile("cookie");
        builder.addHeader("Cookie", cookie);

        // -------------------------- RxJava的方式 --------------------------
//        Observable.just(LattePreference.getCustomAppProfile("cookie"))
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String cookie) throws Exception {
//                        // 给原生API请求附带上WebView拦截下来的Cookie
//                        builder.addHeader("Cookie", cookie);
//                    }
//                });
        return chain.proceed(builder.build());
    }
}
