package com.lqr.latteec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.IError;
import com.lqr.latte.core.net.callback.IFailure;
import com.lqr.latte.core.net.callback.IRequest;
import com.lqr.latte.core.net.callback.ISuccess;

import java.util.WeakHashMap;


public class ExampleFragment extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        RestClient.builder()
                .url("http://httpbin.org/get")
                .params(new WeakHashMap<String, Object>() {
                    {
                        put("string", "value");
                    }
                })
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
                        Toast.makeText(Latte.getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .onError(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();

    }
}
