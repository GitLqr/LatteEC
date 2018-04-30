package com.lqr.latteec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.net.interceptor.DebugInterceptor;
import com.lqr.latte.ec.database.DatabaseManager;
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
                .withInterceptor(new DebugInterceptor("sort_list.php", R.raw.sort_list))
                .withInterceptor(new DebugInterceptor("sort_content_list.php", R.raw.sort_content_data_1))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .configure();
        initStetho();
        DatabaseManager.getInstance().init(getApplicationContext());
    }

    /**
     * 初始化Stetho
     * <p>
     * 打开Chrome输入： chrome://inspect
     */
    private void initStetho() {
        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }
}
