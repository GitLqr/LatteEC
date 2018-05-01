package com.lqr.latte.core.delegates.web.event;

import com.lqr.latte.core.util.log.LatteLogger;

/**
 * Created by lqr on 2018/5/1.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefineEvent", params);
        return null;
    }
}
