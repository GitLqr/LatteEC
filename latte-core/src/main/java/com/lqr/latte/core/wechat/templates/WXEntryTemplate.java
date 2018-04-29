package com.lqr.latte.core.wechat.templates;

import com.lqr.latte.core.wechat.BaseWXEntryActivity;
import com.lqr.latte.core.wechat.LatteWeChat;

/**
 * 创建者：CSDN_LQR
 * 描述：微信入口类模板
 */
public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish(); // 这个界面不要显示出来（微信登录后，会启动第三方app的微信入口类界面，但一般这个界面不用于显示）
        overridePendingTransition(0, 0); // 不要有过渡动画
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
