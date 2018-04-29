package com.lqr.latteec.example.generators;

import com.lqr.latte.annotations.PayEntryGenerator;
import com.lqr.latte.core.wechat.templates.WXPayEntryTemplate;

@PayEntryGenerator(
        packageName = "com.lqr.latteec.example",
        payEntryTemplete = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
