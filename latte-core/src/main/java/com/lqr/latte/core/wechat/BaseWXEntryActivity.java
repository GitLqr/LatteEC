package com.lqr.latte.core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqr.latte.core.net.RestClient;
import com.lqr.latte.core.net.callback.IError;
import com.lqr.latte.core.net.callback.IFailure;
import com.lqr.latte.core.net.callback.ISuccess;
import com.lqr.latte.core.util.log.LatteLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;


/**
 * 创建者：CSDN_LQR
 * 描述：微信登录回调界面基类
 * <p>
 * 微信登录流程分三步：
 * 1、用WXAPI调用微信APP登录授权界面，会回调onResp()，获取code。
 * 2、根据code拼接authUrl后，使用网络请求工具（get请求）获取accessToken和openId。
 * 3、根据accessToken和openId拼接userInfoUrl后，使用网络请求工具（get请求）获取用户信息。
 */
public abstract class BaseWXEntryActivity extends BaseWXActivity {

    // 用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    // 微信发送请求到第三方应用后的回调
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp resp) {
        final String code = ((SendAuth.Resp) resp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteWeChat.APP_ID)
                .append("&secret=")
                .append(LatteWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        LatteLogger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        RestClient
                .builder()
                .url(authUrl)
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("open_id");
                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        LatteLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient
                .builder()
                .url(userInfoUrl)
                .onSuccess(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .onFailure(new IFailure() {
                    @Override
                    public void onFailure(String msg) {

                    }
                })
                .onError(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}