package com.lqr.latte.core.app;

import com.lqr.latte.core.util.LattePreference;

/**
 * 创建者：CSDN_LQR
 * 描述：账户状态管理类
 */
public class AccountManager {
    private enum SignTag {
        SIGN_TAG
    }

    public static void setSignState(boolean state) {
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
