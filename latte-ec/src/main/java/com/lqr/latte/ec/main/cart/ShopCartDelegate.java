package com.lqr.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.ISuccess;
import com.lqr.latte.core.ui.recycler.MultipleItemEntity;
import com.lqr.latte.core.util.log.LatteLogger;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.R2;
import com.lqr.latte.ec.pay.FastPay;
import com.lqr.latte.ec.pay.IAlipayResultListener;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者：CSDN_LQR
 * 描述：购物车
 */
public class ShopCartDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener, IAlipayResultListener {

    private ShopCartAdapter mAdapter;
    // 购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRvShopCart;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelectedAll;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice;
    @BindView(R2.id.tv_shop_cart_pay)
    AppCompatTextView mTvPay;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelectedAll.getTag();
        if (tag == 0) {
            mIconSelectedAll.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
            mIconSelectedAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            // 更新RecyclerView的显示状态
            // mAdapter.notifyDataSetChanged(); // 会闪烁，用下面的方法刷新item
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectedAll.setTextColor(Color.GRAY);
            mIconSelectedAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        // 要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }
        for (MultipleItemEntity entity : deleteEntities) {
            int removePosition;
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            if (entityPosition > mCurrentCount - 1) {
                removePosition = entityPosition - (mTotalCount - mCurrentCount);
            } else {
                removePosition = entityPosition;
            }
            if (removePosition <= mAdapter.getItemCount()) {
                mAdapter.remove(removePosition);
                mCurrentCount = mAdapter.getItemCount();
                // 更新数据
                mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
            }
        }
        checkItemCount();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        if (mAdapter.getItemCount() > 0) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
            checkItemCount();
        }
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay() {
        createOrder();
    }

    /**
     * 创建订单，注意：与支付是没有关系的
     */
    private void createOrder() {
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid", "");
        orderParams.put("amount", 0.01);
        orderParams.put("comment", "测试支付");
        orderParams.put("type", 1);
        orderParams.put("ordertype", 0);
        orderParams.put("isanonymous", true);
        orderParams.put("followeduser", 0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // 进行具体的支付
                        LatteLogger.d("ORDER", response);
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setAlipayResultListener(ShopCartDelegate.this)
                                .setOrderID(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            @SuppressLint("RestrictedApi") final View stubView = mStubNoItem.inflate();
            AppCompatTextView tvStubToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvStubToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你该去购物啦！", Toast.LENGTH_SHORT).show();
                }
            });
            mRvShopCart.setVisibility(View.GONE);
        } else {
            mRvShopCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectedAll.setTag(0);
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
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvShopCart.setLayoutManager(manager);
        mRvShopCart.setAdapter(mAdapter);
        mTvTotalPrice.setText(String.valueOf(mAdapter.getTotalPrice()));
        checkItemCount();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}

