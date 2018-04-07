package com.lqr.latte.core.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.lqr.latte.core.R;
import com.lqr.latte.core.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 创建者：CSDN_LQR
 * 描述：Activity基类
 */
public abstract class ProxyActivity extends SupportActivity {

    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);// v7包中的FragmLayout
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState == null){ // 第一次开启Activity
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 因为是单个Activity+多个Fragment的架构，所以当这个Activity退出时，即整个App退出。
        System.gc();
        System.runFinalization();
    }
}
