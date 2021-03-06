package com.lqr.latteec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.lqr.latte.core.activities.ProxyActivity;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.ec.launcher.ILauncherListener;
import com.lqr.latte.ec.launcher.LauncherDelegate;
import com.lqr.latte.ec.launcher.OnLauncherFinishTag;
import com.lqr.latte.ec.main.EcBottomDelegate;
import com.lqr.latte.ec.sign.ISignListener;
import com.lqr.latte.ec.sign.SignInDelegate;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
//        return new EcBottomDelegate();
        return new LauncherDelegate();
//        return new LauncherScrollDelegate();
//        return new SignUpDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        loadRootFragment(R.id.delegate_container, new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
        loadRootFragment(R.id.delegate_container, new EcBottomDelegate());
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(getApplicationContext(), "用户登录了", Toast.LENGTH_SHORT).show();
                startWithPop(new EcBottomDelegate());
//                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(getApplicationContext(), "用户还未登录", Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
