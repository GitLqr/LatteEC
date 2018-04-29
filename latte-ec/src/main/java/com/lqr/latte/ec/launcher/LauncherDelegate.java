package com.lqr.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lqr.latte.core.app.AccountManager;
import com.lqr.latte.core.app.IUserChecker;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.util.LattePreference;
import com.lqr.latte.core.util.timer.BaseTimerTask;
import com.lqr.latte.core.util.timer.ITimerListener;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者：CSDN_LQR
 * 描述：启动页
 */
public class LauncherDelegate extends LatteDelegate implements ITimerListener {

    private Timer mTimer;
    private BaseTimerTask mTimerTask;
    private int mCount = 5;
    private ILauncherListener mLauncherListener;

    @BindView(R2.id.tv_launcer_timer)
    TextView mTvTimer;

    @OnClick(R2.id.tv_launcer_timer)
    void onClickTimerView() {
        checkIsShowScroll();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  ILauncherListener){
            mLauncherListener = (ILauncherListener) activity;
        }
    }

    private void initTimer() {
        mTimer = new Timer();
        mTimerTask = new BaseTimerTask(this);
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTime() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer != null) {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s", mCount));
                    mCount--;
                    if (mCount < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

    /**
     * 检查是否显示滚动启动页
     */
    private void checkIsShowScroll() {
        if (!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            // 检查用户是否登录APP
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    mLauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }

                @Override
                public void onNotSignIn() {
                    mLauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            });
        }
    }
}
