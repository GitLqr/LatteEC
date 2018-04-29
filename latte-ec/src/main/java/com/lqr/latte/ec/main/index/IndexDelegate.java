package com.lqr.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.ec.R;

/**
 * 创建者：CSDN_LQR
 * 描述：主页
 */
public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
