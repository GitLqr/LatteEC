package com.lqr.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.ISuccess;
import com.lqr.latte.core.ui.recycler.MultipleItemEntity;
import com.lqr.latte.ec.R;
import com.lqr.latte.ec.R2;
import com.lqr.latte.ec.main.sort.SortDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * 创建者：CSDN_LQR
 * 描述：分类——左侧列表
 */
public class VerticalListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRvList;

    @Override
    public Object setLayout() {
        return R.layout.delegate_sort_vertical_list;
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvList.setLayoutManager(manager);
        // 屏蔽动画效果
        mRvList.setItemAnimator(null);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("sort_list.php")
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<MultipleItemEntity> data =
                                new VerticalListDataConverter().setJsonData(response).convert();
                        final SortDelegate delegate = getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, delegate);
                        mRvList.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
