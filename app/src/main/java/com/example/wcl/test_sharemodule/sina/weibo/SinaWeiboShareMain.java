package com.example.wcl.test_sharemodule.sina.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wcl.test_sharemodule.sina.weibo.tokens.AccessTokenKeeper;
import com.example.wcl.test_sharemodule.sina.weibo.tokens.SinaWeiboConstants;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 新浪微博主类
 * <p/>
 * Created by C.L.Wang on 15-1-13.
 */
@SuppressWarnings("unused")
public class SinaWeiboShareMain {

    private IWeiboShareAPI mWeiboShareAPI; // 微博分享实例

    private Activity mActivity; // 活动

    @SuppressWarnings("unused")
    public SinaWeiboShareMain(Activity activity) {
        mActivity = activity;

        // 初始化微博分享API
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, SinaWeiboConstants.APP_KEY);
        mWeiboShareAPI.registerApp();

    }

    /**
     * 处理回调请求。
     * 默认参数{@link SinaWeiboShareResponse}
     *
     * @param response 处理请求接口
     */
    @SuppressWarnings("unused")
    public void handleWeiboResponse(Intent intent, IWeiboHandler.Response response) {
        mWeiboShareAPI.handleWeiboResponse(intent, response);
    }

    /**
     * 发送消息。
     *
     * @param msg 消息
     */
    @SuppressWarnings("unused")
    public void sendMessage(String msg) {

        TextObject textObject = new TextObject();
        textObject.text = msg;

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = textObject;

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        AuthInfo authInfo = new AuthInfo(mActivity, SinaWeiboConstants.APP_KEY, SinaWeiboConstants.REDIRECT_URL, SinaWeiboConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        mWeiboShareAPI.sendRequest(mActivity, request, authInfo, token,
                new WeiboAuthListener() {
                    @Override
                    public void onWeiboException(WeiboException arg0) {
                    }

                    @Override
                    public void onComplete(Bundle bundle) {
                        // TODO Auto-generated method stub
                        Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                        AccessTokenKeeper.writeAccessToken(mActivity, newToken);
                        Toast.makeText(mActivity,
                                "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }
}
