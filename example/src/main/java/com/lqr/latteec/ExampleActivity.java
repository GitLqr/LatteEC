package com.lqr.latteec;

import com.lqr.latte.core.activities.ProxyActivity;
import com.lqr.latte.core.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleFragment();
    }
}
