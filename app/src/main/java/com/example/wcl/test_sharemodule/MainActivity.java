package com.example.wcl.test_sharemodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wcl.test_sharemodule.sina.weibo.SinaWeiboShareMain;
import com.example.wcl.test_sharemodule.sina.weibo.SinaWeiboShareResponse;
import com.example.wcl.test_sharemodule.tencent.qq.TencentQQShareMain;
import com.example.wcl.test_sharemodule.tencent.wechat.TencentWeChatShareMain;

public class MainActivity extends Activity {

    private static final String TAG = "DEBUG-WCL: MainActivity";

//    public static Tencent mTencent;
//    private int mExtarFlag = 0x00;

    private EditText mShareMessageEditText; // 分享内容
    private Button mShareSinaWeiboButton; // 分享到新浪微博
    private Button mShareTencentWechatButton; // 分享到微信
    private Button mShareTencentWechatMomentsButton; // 分享到朋友圈
    private Button mShareTencentQQButton; // 分享到QQ
    private Button mShareTencentQQZoneButton; // 分享到QQ空间
    private Button mShareTencentWeiboButton; // 分享到QQ微博

    private SinaWeiboShareMain mSinaWeiboMain; // 新浪微博分享主类
    private SinaWeiboShareResponse mSinaWeiboResponse; // 回调函数主类
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

//        mTencent = Tencent.createInstance("1101774620", this);

        mSinaWeiboMain = new SinaWeiboShareMain(this);
        mSinaWeiboResponse = new SinaWeiboShareResponse(this);
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
            String msg = mShareMessageEditText.getText().toString();
            mSinaWeiboMain.sendMessage(msg);
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
