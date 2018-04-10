package com.lqr.latte.core.net.interceptor;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;


public abstract class BaseInterceptor implements Interceptor {

    public LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final Request request = chain.request();
        final HttpUrl httpUrl = request.url();
        final int size = httpUrl.querySize();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(httpUrl.queryParameterName(i), httpUrl.queryParameterValue(i));
        }
        return params;
    }

    public String getUrlParameters(Chain chain, String key) {
        final Request request = chain.request();
        HttpUrl httpUrl = request.url();
        return httpUrl.queryParameter(key);
    }

    public LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final Request request = chain.request();
        final FormBody formBody = (FormBody) request.body();
        final int size = formBody.size();
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;
    }

    public String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }
}
