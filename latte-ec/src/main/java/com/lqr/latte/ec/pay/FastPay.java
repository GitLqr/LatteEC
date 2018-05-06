package com.lqr.latte.ec.pay;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqr.latte.core.app.ConfigType;
import com.lqr.latte.core.app.Latte;
import com.lqr.latte.core.delegates.LatteDelegate;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.ISuccess;
import com.lqr.latte.core.ui.loader.LatteLoader;
import com.lqr.latte.core.util.log.LatteLogger;
import com.lqr.latte.core.wechat.LatteWeChat;
import com.lqr.latte.ec.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * 创建者：CSDN_LQR
 * 描述：快速支付
 * <p>
 * 1、支付宝支付
 * 2、微信支付
 */
public class FastPay implements View.OnClickListener {

    // 设置支付回调监听
    private IAlipayResultListener mAlipayResultListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOrderID = -1;

    private FastPay(LatteDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext())
                .create();
    }

    public static FastPay create(LatteDelegate delegate) {
        return new FastPay(delegate);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alipay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }
    }

    public FastPay setAlipayResultListener(IAlipayResultListener alipayResultListener) {
        mAlipayResultListener = alipayResultListener;
        return this;
    }

    public FastPay setOrderID(int orderId) {
        mOrderID = orderId;
        return this;
    }

    private final void alipay(int orderId) {
        // final String signUrl = "你的服务端支付地址" + orderId;
        final String signUrl = "http://app.api.zanzuanshi.com/api/v1/alipay/a=" + orderId;
        RestClient.builder()
                .url(signUrl)
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        LatteLogger.d("PAY_SIGN", paySign);
                        // 必须是异步的调用客户端支付接口
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mAlipayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paySign);
                    }
                })
                .build()
                .post();
    }

    private final void weChatPay(int orderId) {
        LatteLoader.stopLoading();
        // final String weChatPrePayUrl = "你的服务端微信预支付地址" + orderId;
        final String weChatPrePayUrl = "http://app.api.zanzuanshi.com/api/v1/wxpay/prepay/a=" + orderId;
        LatteLogger.d("WX_PAY", weChatPrePayUrl);

        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String appId = Latte.getConfiguration(ConfigType.WE_CHAT_APP_ID);
        iwxapi.registerApp(appId);
        RestClient.builder()
                .url(weChatPrePayUrl)
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dialog_pay_alipay) {
            mDialog.cancel();
            alipay(mOrderID);
        } else if (id == R.id.btn_dialog_pay_wechat) {
            mDialog.cancel();
            weChatPay(mOrderID);
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }
    }
}
