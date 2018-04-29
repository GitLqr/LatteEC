package com.lqr.latte.core.app;

/**
 * 创建者：CSDN_LQR
 * 描述：配置类型
 */
public enum ConfigType {
    API_HOST, // 网络请求的 api host
    APPLICATION_CONTEXT, // 全局上下文
    ACTIVITY,// 全局Activity（该框架的设计理念是 1个Activity + n个Fragment，所以可以这样，但如果是多个Activity则需要重新考虑一下）
    CONFIG_READY, // 是否配置完毕标记
    ICON, // iconify
    LOADER_DELAYED,
    INTERCEPTOR, //拦截器
    WE_CHAT_APP_ID, // 微信平台 app_id
    WE_CHAT_APP_SECRET, // 微信平台 app_secret
}
