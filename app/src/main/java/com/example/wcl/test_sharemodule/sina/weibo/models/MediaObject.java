package com.example.wcl.test_sharemodule.sina.weibo.models;

import android.graphics.Bitmap;

/**
 * 多媒体对象
 * <p/>
 * Created by C.L.Wang on 15-1-15.
 */
@SuppressWarnings("unused")
public class MediaObject {

    // 分享类型
    public enum Type {
        WEBPAGE, MUSIC, VIDEO, VOICE
    }

    private String mTitle; // 标题
    private String mShareDesc; // 分享描述
    private Bitmap mThumbBitmap; // 缩略图
    private String mShareUrl; // 分享链接
    private String mDataUrl; // 数据URL（可以忽略）
    private String mDataHdUrl; // 高清数据URL（可以忽略）
    private int mDuration; // 持续时间（可以忽略）
    private String mDefaultText; // 默认描述（可以忽略）
    private Type mType; // 分享类型（可以忽略）

    /**
     * 多媒体对象
     *
     * @param title       标题
     * @param shareDesc   分享描述
     * @param thumbBitmap 缩略图
     * @param shareUrl    分享链接
     */
    public MediaObject(String title, String shareDesc, Bitmap thumbBitmap, String shareUrl) {
        this(title, shareDesc, thumbBitmap, shareUrl,
                "http://www.chunyuyisheng.com/",
                "http://www.chunyuyisheng.com/",
                10,
                "默认分享的多媒体对象",
                Type.WEBPAGE);
    }

    // Webpage网页的对象
    public MediaObject(String title, String shareDesc, Bitmap thumbBitmap,
                       String shareUrl, String defaultText, Type type) {
        this(title, shareDesc, thumbBitmap, shareUrl, null, null, 0, defaultText, type);
    }

    // Music、Video、Voice的对象
    public MediaObject(String title, String shareDesc, Bitmap thumbBitmap,
                       String shareUrl, String dataUrl, String dataHdUrl,
                       int duration, String defaultText, Type type) {
        mTitle = title;
        mShareDesc = shareDesc;
        mThumbBitmap = thumbBitmap;
        mShareUrl = shareUrl;
        mDataUrl = dataUrl;
        mDataHdUrl = dataHdUrl;
        mDuration = duration;
        mDefaultText = defaultText;
        mType = type;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getShareDesc() {
        return mShareDesc;
    }

    public void setShareDesc(String shareDesc) {
        mShareDesc = shareDesc;
    }

    public Bitmap getThumbBitmap() {
        return mThumbBitmap;
    }

    public void setThumbBitmap(Bitmap thumbBitmap) {
        mThumbBitmap = thumbBitmap;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
    }

    public String getDataHdUrl() {
        return mDataHdUrl;
    }

    public void setDataHdUrl(String dataHdUrl) {
        mDataHdUrl = dataHdUrl;
    }

    public String getDataUrl() {
        return mDataUrl;
    }

    public void setDataUrl(String dataUrl) {
        mDataUrl = dataUrl;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public String getDefaultText() {
        return mDefaultText;
    }

    public void setDefaultText(String defaultText) {
        mDefaultText = defaultText;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

}
