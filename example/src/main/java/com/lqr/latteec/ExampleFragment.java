package com.lqr.latteec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.IError;
import com.lqr.latte.core.net.callback.IFailure;
import com.lqr.latte.core.net.callback.IRequest;
import com.lqr.latte.core.net.callback.ISuccess;


public class ExampleFragment extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        RestClient.builder()
//                .url("http://httpbin.org/get")
//                .params(new WeakHashMap<String, Object>() {
//                    {
//                        put("string", "value");
//                    }
//                })
                .url("http://127.0.0.1/index")
                .loader(getActivity())
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("lqr", response);
                        Toast.makeText(Latte.getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure(String msg) {
                        Log.e("lqr", "onFailure msg = " + msg);
                    }
                })
                .onError(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Log.e("lqr", "onError code = " + code + ", msg = " + msg);
                    }
                })
                .build()
                .get();

    }
}
