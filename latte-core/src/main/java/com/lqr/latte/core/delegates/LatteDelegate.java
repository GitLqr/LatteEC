package com.lqr.latte.core.delegates;

/**
 * 创建者：CSDN_LQR
 * 描述：真正的Fragment基类
 */
public abstract class LatteDelegate extends PermissionCheckerDelegate {

    public <T extends LatteDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
