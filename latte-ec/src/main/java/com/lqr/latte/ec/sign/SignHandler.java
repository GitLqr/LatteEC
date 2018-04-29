package com.lqr.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lqr.latte.core.app.AccountManager;
import com.lqr.latte.ec.database.DatabaseManager;
import com.lqr.latte.ec.database.UserProfile;

/**
 * 创建者：CSDN_LQR
 * 描述：注册控制类
 */
public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getUserProfileDao().insert(profile);

        AccountManager.setSignState(true);
        if (signListener != null) {
            signListener.onSignInSuccess();
        }
    }

    public static void onSignUp(String response, ISignListener signListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getUserProfileDao().insert(profile);

        AccountManager.setSignState(true);
        if (signListener != null) {
            signListener.onSignUpSuccess();
        }
    }
}
