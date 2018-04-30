package com.lqr.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.main.sort.content.ContentDelegate;
import com.lqr.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * 创建者：CSDN_LQR
 * 描述：分类
 */
public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 左侧——列表
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        // 右侧——内容
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(0));
    }
}
