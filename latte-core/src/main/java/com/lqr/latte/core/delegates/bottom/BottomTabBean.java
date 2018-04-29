package com.lqr.latte.core.delegates.bottom;

/**
 * 创建者：CSDN_LQR
 * 描述：底部Tab封装
 * <p>
 * 框架设计，icon是icon图形文字，不是图片！
 */
public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
