package com.lqr.latte.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by lqr on 2018/4/30.
 */

public class MultipleViewHolder extends BaseViewHolder {
    private MultipleViewHolder(View view) {
        super(view);
    }

    public static MultipleViewHolder create(View view) {
        return new MultipleViewHolder(view);
    }
}
