package com.lqr.latte.core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;


/**
 * 创建者：CSDN_LQR
 * 描述：
 */
public class Latte {

    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getLatteConfigs();
    }

    public static <T> T getConfiguration(Enum<ConfigType> type) {
        return Configurator.getInstance().getConfiguration(type);
    }

    public static Context getApplicationContext() {
        // return Configurator.getInstance().getConfiguration(ConfigType.APPLICATION_CONTEXT);
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigType.HANDLER);
    }

}
