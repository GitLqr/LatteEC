package com.lqr.latteec;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.net.interceptor.DebugInterceptor;
import com.lqr.latte.ec.icon.FontEcModule;

/**
 * 创建者：CSDN_LQR
 * 描述：自定义Application
 */
public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(getApplicationContext())
                .withIcon(new FontAwesomeModule()) // 具体字体图标到 https://fontawesome.com/ 查找
                .withIcon(new FontEcModule())
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
    }
}
