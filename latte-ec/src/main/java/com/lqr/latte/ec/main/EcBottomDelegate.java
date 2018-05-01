package com.lqr.latte.ec.main;

import android.graphics.Color;

import com.lqr.latte.core.delegates.bottom.BaseBottomDelegate;
import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.core.delegates.bottom.BottomTabBean;
import com.lqr.latte.core.delegates.bottom.ItemBuilder;
import com.lqr.latte.ec.main.discover.DiscoverDelegate;
import com.lqr.latte.ec.main.index.IndexDelegate;
import com.lqr.latte.ec.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * 创建者：CSDN_LQR
 * 描述：首页（带底部Tab）
 */
public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        LinkedHashMap<BottomTabBean, BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}", "主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}", "分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}", "发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}", "购物车"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-user}", "我的"), new IndexDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
