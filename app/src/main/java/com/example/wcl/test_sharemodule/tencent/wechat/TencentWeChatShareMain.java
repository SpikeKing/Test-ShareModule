package com.example.wcl.test_sharemodule.tencent.wechat;

import android.app.Activity;

import com.example.wcl.test_sharemodule.tencent.wechat.tokens.TencentWechatConstants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

public class TencentWeChatShareMain {

    private IWXAPI mApi; // 微信API
    private Activity mActivity;

    @SuppressWarnings("unused")
    public TencentWeChatShareMain(Activity activity) {

        mActivity = activity;
        mApi = WXAPIFactory.createWXAPI(mActivity, TencentWechatConstants.APP_ID);
        mApi.registerApp(TencentWechatConstants.APP_ID);
    }

    @SuppressWarnings("unused")
    public void sendMessageByMoments(String msg) {
        String text = msg;

        if (text == null || text.length() == 0) {
            return;
        }

        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = textObj;
        mediaMessage.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = mediaMessage;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;

        mApi.sendReq(req);
    }

    @SuppressWarnings("unused")
    public void sendMessageByWechat(String msg) {
        String text = msg;

        if (text == null || text.length() == 0) {
            return;
        }

        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage mediaMsg = new WXMediaMessage();
        mediaMsg.mediaObject = textObj;
        mediaMsg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = mediaMsg;
        req.scene = SendMessageToWX.Req.WXSceneSession;

        mApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ?
                String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
