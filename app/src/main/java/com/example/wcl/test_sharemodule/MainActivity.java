package com.example.wcl.test_sharemodule;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wcl.test_sharemodule.sina.weibo.WeiboShareMain;
import com.example.wcl.test_sharemodule.sina.weibo.models.MediaObject;
import com.example.wcl.test_sharemodule.sina.weibo.tokens.WeiboShareResponse;
import com.example.wcl.test_sharemodule.tencent.qq.TencentQQShareMain;
import com.example.wcl.test_sharemodule.tencent.wechat.TencentWeChatShareMain;

public class MainActivity extends Activity {

    private static final String TAG = "DEBUG-WCL: MainActivity";

    private EditText mShareMessageEditText; // 分享内容
    private Button mShareSinaWeiboButton; // 分享到新浪微博
    private Button mShareTencentWechatButton; // 分享到微信
    private Button mShareTencentWechatMomentsButton; // 分享到朋友圈
    private Button mShareTencentQQButton; // 分享到QQ
    private Button mShareTencentQQZoneButton; // 分享到QQ空间
    private Button mShareTencentWeiboButton; // 分享到QQ微博

    private WeiboShareMain mSinaWeiboMain; // 新浪微博分享主类
    private WeiboShareResponse mSinaWeiboResponse; // 回调函数主类
    private TencentWeChatShareMain mTencentWeChatMain; // 腾讯微信分享主类
    private TencentQQShareMain mTencentQQMain; // 腾讯QQ分享主类

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mShareMessageEditText = (EditText) findViewById(R.id.share_module_share_message_edit_text);

        mShareSinaWeiboButton = (Button) findViewById(R.id.share_module_share_sina_weibo_button);
        mShareTencentWechatButton = (Button) findViewById(R.id.share_module_share_tencent_wechat_button);
        mShareTencentWechatMomentsButton = (Button) findViewById(R.id.share_module_share_tencent_wechat_moments_button);
        mShareTencentQQButton = (Button) findViewById(R.id.share_module_share_tencent_qq_button);
        mShareTencentQQZoneButton = (Button) findViewById(R.id.share_module_share_tencent_qq_zone_button);
        mShareTencentWeiboButton = (Button) findViewById(R.id.share_module_share_tencent_weibo_button);

        mShareSinaWeiboButton.setOnClickListener(mSinaWeiboListener);
        mShareTencentWechatButton.setOnClickListener(mTencentWechatListener);
        mShareTencentWechatMomentsButton.setOnClickListener(mTencentWechatMomentsListener);
        mShareTencentQQButton.setOnClickListener(mTencentQQListener);
        mShareTencentQQZoneButton.setOnClickListener(mTencentQQZoneListener);

        mShareTencentWeiboButton.setOnClickListener(mListener);

        mSinaWeiboMain = new WeiboShareMain(this);
        mSinaWeiboResponse = new WeiboShareResponse(this);
        if (savedInstanceState != null) {
            mSinaWeiboMain.handleWeiboResponse(getIntent(), mSinaWeiboResponse);
        }

        mTencentWeChatMain = new TencentWeChatShareMain(this);

        mTencentQQMain = new TencentQQShareMain(this);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "加油", Toast.LENGTH_LONG).show();
        }
    };

    View.OnClickListener mSinaWeiboListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String format = "%1$s（分享自 春雨医生 %2$s）";
            String content = "春雨医生分享测试";
            String downloadUrl = "http://www.chunyuyisheng.com/download/chunyu/latest/";
            String text = String.format(format, content, downloadUrl);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chunyu_title);

            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.chunyu_icon);
            MediaObject webpage = new MediaObject(
                    "春雨医生",
                    "春雨医生主页",
                    bitmap,
                    "http://www.chunyuyisheng.com/index"
            );

            MediaObject video = new MediaObject(
                    "春雨医生",
                    "春雨医生介绍视频",
                    bitmap,
                    "http://media.chunyuyisheng.com/fwb/static/images/home/video/video_aboutCY_A.mp4"
            );

            String demoTitle = text;
            Bitmap demoShareBitmap = icon;
            MediaObject demoWebpage = webpage;
//            MediaObject demoVideo = video;

            mSinaWeiboMain.sendMultiMediaObjects(null, null, demoWebpage);
        }
    };

    View.OnClickListener mTencentWechatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mShareMessageEditText.getText().toString();
            mTencentWeChatMain.sendMessageByWechat(msg);
        }
    };

    View.OnClickListener mTencentWechatMomentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mShareMessageEditText.getText().toString();
            mTencentWeChatMain.sendMessageByMoments(msg);
        }
    };

    View.OnClickListener mTencentQQListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mShareMessageEditText.getText().toString();
            mTencentQQMain.sendMessageByQQ(msg);
        }
    };

    View.OnClickListener mTencentQQZoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mShareMessageEditText.getText().toString();
            mTencentQQMain.sendMessageByZone(msg);
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mSinaWeiboMain.handleWeiboResponse(intent, mSinaWeiboResponse);
    }


}
