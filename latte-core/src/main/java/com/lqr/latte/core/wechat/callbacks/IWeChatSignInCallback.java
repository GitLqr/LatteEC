package com.lqr.latte.core.wechat.callbacks;

/**
 * 创建者：CSDN_LQR
 * 描述：微信登录成功回调
 */
public interface IWeChatSignInCallback {
    void onSignInSuccess(String userInfo);
}
