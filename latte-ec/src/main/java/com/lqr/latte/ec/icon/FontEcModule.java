package com.lqr.latte.ec.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 创建者：CSDN_LQR
 * 描述：电商 FontModule
 * <p>
 * 仿{@link com.joanzapata.iconify.fonts.FontAwesomeModule}编写
 */
public class FontEcModule implements IconFontDescriptor {


    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return EcIcon.values();
    }
}
