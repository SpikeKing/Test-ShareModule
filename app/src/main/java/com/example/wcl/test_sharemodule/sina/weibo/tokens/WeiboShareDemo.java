package com.example.wcl.test_sharemodule.sina.weibo.tokens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.wcl.test_sharemodule.R;
import com.example.wcl.test_sharemodule.sina.weibo.models.MediaObject;

/**
 * Created by wangchenlong on 15-1-16.
 */
public class WeiboShareDemo {

    private Context mContext;

    private String mTitle;
    private Bitmap mShareBitmap;
    private MediaObject mWebpage;
    private MediaObject mVideo;

    public WeiboShareDemo(Context context) {
        mContext = context;
    }

    private void init() {

        String format = "%1$s（分享自 春雨医生 %2$s）";
        String content = "春雨医生分享测试";
        String downloadUrl = "http://www.chunyuyisheng.com/download/chunyu/latest/";
        String text = String.format(format, content, downloadUrl);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chunyu_title);

        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.chunyu_icon);
//        MediaObject webpage = new MediaObject(
//                "春雨医生",
//                "春雨医生主页",
//                thumb,
//                "http://www.chunyuyisheng.com/index"
//        );
//
//        MediaObject video = new MediaObject(
//                "春雨医生",
//                "春雨医生介绍视频",
//                bitmap,
//                "http://media.chunyuyisheng.com/fwb/static/images/home/video/video_aboutCY_A.mp4"
//        );
    }


}
