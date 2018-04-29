package com.lqr.latte.core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * 创建者：CSDN_LQR
 * 描述：BottomItem构建者，管理 Bottom Tab与Fragment的关联关系
 */
public class ItemBuilder {
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    // 使用简单工厂模式
    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(BottomTabBean bean, BottomItemDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomItemDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomItemDelegate> build() {
        return ITEMS;
    }
}
