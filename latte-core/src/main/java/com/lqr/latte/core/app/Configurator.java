package com.lqr.latte.core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.lqr.latte.core.delegates.web.event.Event;
import com.lqr.latte.core.delegates.web.event.EventManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 创建者：CSDN_LQR
 * 描述：
 */
public class Configurator {

    private static final Handler HANDLER = new Handler();
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator() {
        // 默认标明配置未完成
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY, false);
        LATTE_CONFIGS.put(ConfigType.HANDLER, HANDLER);
    }

    // -------------------------- 使用静态内部类的方式创建线程安全的单例 begin --------------------------
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }
    // -------------------------- 使用静态内部类的方式创建线程安全的单例 end --------------------------

    /**
     * 配置网络请求API host
     */
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    /**
     * 网络请求延时
     */
    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigType.LOADER_DELAYED, delayed);
        return this;
    }

    /**
     * 配置icon字体库（描述符）
     */
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    /**
     * 配置微信apppId
     */
    public final Configurator withWeChatAppId(String appId) {
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appId);
        return this;
    }

    /**
     * 配置微信appSecret
     */
    public final Configurator withWeChatAppSecret(String appSecret) {
        LATTE_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    /**
     * 配置全局Activity
     */
    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    /**
     * 配置web的域名（主要是用于获取cookie）
     */
    public final Configurator withWebHost(@NotNull String webHost) {
        LATTE_CONFIGS.put(ConfigType.WEB_HOST, webHost);
        return this;
    }

    /**
     * 配置JavascriptInterface的注入名
     */
    public final Configurator withJavascriptInterface(@NotNull String name) {
        LATTE_CONFIGS.put(ConfigType.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    /**
     * 配置JavascriptInterface响应事件
     */
    public final Configurator withWebEvent(@NotNull String name, @NotNull Event event) {
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name, event);
        return this;
    }

    /**
     * 手动调用该方法标明配置已完成
     */
    public final void configure() {
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY, true);
    }

    /**
     * 初始化withIcon()方法添加进来的icon字体库
     */
    private final void initIcons() {
        if (ICONS.size() > 0) {
            // 先初始化第0个icon，得到IconifyInitializer对象。
            Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            // 再继续初始化剩下的icon。
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 检查是否配置完成
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 根据枚举类型获取到对应的配置内容
     */
    public final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }

    /**
     * 获取配置映射对象
     */
    public final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }
}
