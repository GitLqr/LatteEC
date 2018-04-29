package com.lqr.latteec.example.generators;

import com.lqr.latte.annotations.EntryGenerator;
import com.lqr.latte.core.wechat.templates.WXEntryTemplate;

@EntryGenerator(
        packageName = "com.lqr.latteec.example",
        entryTemplete = WXEntryTemplate.class
)
public interface WeChatEntry {
}
