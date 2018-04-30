package com.lqr.latte.core.ui.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * 创建者：CSDN_LQR
 * 描述：RecyclerView item 的装饰类
 */
public class BaseDecoration extends DividerItemDecoration {

    private BaseDecoration(@ColorInt int color, int size) {
        setDividerLookup(new DividerLookupImpl(color, size));
    }

    public static BaseDecoration create(@ColorInt int color, int size) {
        return new BaseDecoration(color, size);
    }
}
