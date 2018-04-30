package com.lqr.latte.ec.main.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 创建者：CSDN_LQR
 * 描述：商品详情界面
 */
public class GoodsDetailDelegate extends LatteDelegate {

    public static GoodsDetailDelegate create() {
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator(); // 默认是竖直动画，这里使用水平动画
    }
}
