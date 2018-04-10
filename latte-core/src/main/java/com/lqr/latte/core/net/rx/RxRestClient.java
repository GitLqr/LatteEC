package com.lqr.latte.core.net.rx;


import android.content.Context;

import com.lqr.latte.core.net.HttpMethod;
import com.lqr.latte.core.net.RestCreator;
import com.lqr.latte.core.ui.LatteLoader;
import com.lqr.latte.core.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRestClient {

    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final RequestBody BODY;
    private final File FILE;
    private final Context CONTEXT;
    private final LoaderStyle LOADER_STYLE;

    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        Context context,
                        LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RxRestClientBuilder builder() {
        return new RxRestClientBuilder();
    }

    public Observable<String> request(HttpMethod httpMethod) {
        RxRestService service = RestCreator.getRxRestService();
        Observable<String> observable = null;
        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (httpMethod) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }

        return observable;
    }

    public Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public Observable<String> post() {
        if (BODY != null) {
            return request(HttpMethod.POST_RAW);
        } else {
            if (PARAMS.isEmpty()) {
                throw new RuntimeException("params must not be null");
            } else {
                return request(HttpMethod.POST);
            }
        }
    }

    public Observable<String> put() {
        if (BODY != null) {
            return request(HttpMethod.PUT_RAW);
        } else {
            if (PARAMS.isEmpty()) {
                throw new RuntimeException("params must not be null");
            } else {
                return request(HttpMethod.PUT);
            }
        }
    }

    public Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public Observable<ResponseBody> download() {
        return RestCreator.getRxRestService().download(URL,PARAMS);
    }
}
