package com.lqr.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.lqr.latte.core.delegates.bottom.BottomItemDelegate;
import com.lqr.latte.core.ui.refresh.RefreshHandler;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.R2;

import butterknife.BindView;

/**
 * 创建者：CSDN_LQR
 * 描述：主页
 */
public class IndexDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_index)
    RecyclerView mRvIndex;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mSrlIndex;
    @BindView(R2.id.tb_index)
    Toolbar mTbIndex;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconIndexScan;
    @BindView(R2.id.icon_index_message)
    IconTextView mIconIndexMessage;
    private RefreshHandler mRefreshHandler;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mSrlIndex, mRvIndex, new IndexDataConverter());
    }

    private void initRefreshLayout() {
        mSrlIndex.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mSrlIndex.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRvIndex.setLayoutManager(manager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

}
