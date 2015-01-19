package com.example.wcl.test_sharemodule.sina.weibo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wcl.test_sharemodule.sina.weibo.models.MediaObject;
import com.example.wcl.test_sharemodule.sina.weibo.tokens.AccessTokenKeeper;
import com.example.wcl.test_sharemodule.sina.weibo.tokens.WeiboConstants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * 新浪微博分享的主类
 * <p/>
 * Created by C.L.Wang on 15-1-13.
 */
public class WeiboShareMain {

    private static final String TAG = "DEBUG-WCL: " +
            WeiboShareMain.class.getClass().getSimpleName();

    private IWeiboShareAPI mWeiboShareAPI; // 微博分享实例

    private Activity mActivity; // 活动

    // 微博验证监听
    private WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {
        @Override
        public void onComplete(Bundle bundle) {
            // TODO Auto-generated method stub
            Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
            AccessTokenKeeper.writeAccessToken(mActivity, newToken);
            Toast.makeText(mActivity,
                    "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
        }

        @Override
        public void onCancel() {
        }
    };


    @SuppressWarnings("unused")
    public WeiboShareMain(Activity activity) {
        mActivity = activity;

        // 初始化微博分享API，需要APP_KEY
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, WeiboConstants.APP_KEY);
        mWeiboShareAPI.registerApp(); // 注册APP
    }

    /**
     * 处理回调请求。
     * 默认参数{@link com.example.wcl.test_sharemodule.sina.weibo.tokens.WeiboShareResponse}
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
     * @param shareText   消息
     * @param shareBitmap 图片
     */
    @SuppressWarnings("unused")
    public void sendMultiMediaObjects(
            String shareText, Bitmap shareBitmap,
            MediaObject mediaObject) {

        if (shareText == null && shareBitmap == null && mediaObject == null) {
            Toast.makeText(mActivity, "分享对象为空", Toast.LENGTH_LONG).show();
        }

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

        if (shareText != null) {
            weiboMessage.textObject = getTextObj(shareText); // 分享的文本
        }
        if (shareBitmap != null) {
            weiboMessage.imageObject = getImageObj(shareBitmap); // 分享的图片
        }

        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (mediaObject != null) {
            setMediaObject(weiboMessage, mediaObject);
        }

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        AuthInfo authInfo = new AuthInfo(mActivity, WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        mWeiboShareAPI.sendRequest(mActivity, request, authInfo, token, mWeiboAuthListener);
    }

    public void setMediaObject(WeiboMultiMessage weiboMessage, MediaObject mediaObject) {

        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        switch (mediaObject.getType()) {
            case WEBPAGE:
                weiboMessage.mediaObject = getWebpageObj(mediaObject);
                break;
            case MUSIC:
                weiboMessage.mediaObject = getMusicObj(mediaObject);
                break;
            case VIDEO:
                weiboMessage.mediaObject = getVideoObj(mediaObject);
                break;
            case VOICE:
                weiboMessage.mediaObject = getVoiceObj(mediaObject);
                break;
            default:
                break;
        }
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(MediaObject shareWebpage) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareWebpage.getTitle();
        mediaObject.description = shareWebpage.getShareDesc();

        // 设置 Bitmap 类型的图片到网页对象里
        mediaObject.setThumbImage(shareWebpage.getThumbBitmap());
        mediaObject.actionUrl = shareWebpage.getShareUrl();
        mediaObject.defaultText = shareWebpage.getDefaultText();
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(MediaObject shareMusic) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = shareMusic.getTitle();
        musicObject.description = shareMusic.getShareDesc();

        // 设置 Bitmap 类型的图片到音乐对象里
        musicObject.setThumbImage(shareMusic.getThumbBitmap());
        musicObject.actionUrl = shareMusic.getShareUrl();
        musicObject.dataUrl = shareMusic.getDataUrl();
        musicObject.dataHdUrl = shareMusic.getDataHdUrl();
        musicObject.duration = shareMusic.getDuration();
        musicObject.defaultText = shareMusic.getDefaultText();
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(MediaObject shareVideo) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = shareVideo.getTitle();
        videoObject.description = shareVideo.getShareDesc();

        // 设置 Bitmap 类型的图片到视频对象里
        videoObject.setThumbImage(shareVideo.getThumbBitmap());
        videoObject.actionUrl = shareVideo.getShareUrl();
        videoObject.dataUrl = shareVideo.getDataUrl();
        videoObject.dataHdUrl = shareVideo.getDataHdUrl();
        videoObject.duration = shareVideo.getDuration();
        videoObject.defaultText = shareVideo.getDefaultText();
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj(MediaObject shareVoice) {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = shareVoice.getTitle();
        voiceObject.description = shareVoice.getShareDesc();

        // 设置 Bitmap 类型的图片到音频对象里
        voiceObject.setThumbImage(shareVoice.getThumbBitmap());
        voiceObject.actionUrl = shareVoice.getShareUrl();
        voiceObject.dataUrl = shareVoice.getDataUrl();
        voiceObject.dataHdUrl = shareVoice.getDataHdUrl();
        voiceObject.duration = 10;
        voiceObject.defaultText = shareVoice.getDefaultText();
        return voiceObject;
    }
}
