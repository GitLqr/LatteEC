package com.lqr.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * 创建者：CSDN_LQR
 * 描述：电商 Icon
 * <p>
 * 仿{@link com.joanzapata.iconify.fonts.FontAwesomeIcons}编写
 */
public enum EcIcon implements Icon {
    icon_scan('\ue602'), // &#xe602
    icon_ali_pay('\ue606'); // &#xe606

    char character;

    EcIcon(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
