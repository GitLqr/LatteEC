package com.lqr.latte.ec.main.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.ISuccess;
import com.lqr.latte.core.ui.recycler.MultipleItemEntity;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建者：CSDN_LQR
 * 描述：购物车
 */
public class ShopCartDelegate extends BottomItemDelegate implements ISuccess {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRvShopCart;
    private ShopCartAdapter mAdapter;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .onSuccess(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data =
                new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvShopCart.setLayoutManager(manager);
        mRvShopCart.setAdapter(mAdapter);
    }
}

