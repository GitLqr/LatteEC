package com.lqr.latte.core.ui.recycler;

import java.util.LinkedHashMap;

/**
 * 创建者：CSDN_LQR
 * 描述：多数据类型实体构造者（一个类型 ： 多个同类型数据）
 */
public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        // 先清除之前的数据
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(LinkedHashMap<?, ?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build() {
        return new MultipleItemEntity(FIELDS);
    }
}
