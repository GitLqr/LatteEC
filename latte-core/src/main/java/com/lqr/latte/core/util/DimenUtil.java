package com.lqr.latte.core.util;


import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.lqr.latte.core.app.Latte;

public class DimenUtil {

    public static final int getScreenWidth() {
        Resources resources = Latte.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static final int getScreenHeight() {
        Resources resources = Latte.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
