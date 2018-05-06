package com.lqr.latte.ec.pay;

/**
 * 创建者：CSDN_LQR
 * 描述：支付宝支付回调接口
 */
public interface IAlipayResultListener {
    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
