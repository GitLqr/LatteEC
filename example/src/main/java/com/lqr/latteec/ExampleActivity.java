package com.lqr.latteec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.lqr.latte.core.activities.ProxyActivity;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.ec.launcher.LauncherDelegate;

public class ExampleActivity extends ProxyActivity {
F
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
//        return new LauncherScrollDelegate();
    }
}
