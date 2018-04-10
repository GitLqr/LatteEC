package com.lqr.latte.core.app;

/**
 * 创建者：CSDN_LQR
 * 描述：配置类型
 */
public enum ConfigType {
    API_HOST, // 网络请求的 api host
    APPLICATION_CONTEXT, // 全局上下文
    CONFIG_READY, // 是否配置完毕标记
    ICON, // iconify
    LOADER_DELAYED,
    INTERCEPTOR, //拦截器
}
