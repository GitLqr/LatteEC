package com.lqr.latteec.example.generators;

import com.lqr.latte.annotations.AppRegisterGenerator;
import com.lqr.latte.core.wechat.templates.AppRegisterTemplate;

@AppRegisterGenerator(
        packageName = "com.lqr.latteec.example",
        registerTemplete = AppRegisterTemplate.class
)
public interface WeChatAppRegister {
}
