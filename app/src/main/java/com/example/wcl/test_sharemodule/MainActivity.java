package com.example.wcl.test_sharemodule;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText mShareMessageEditText; // 分享内容
    private Button mShareSinaWeiboButton; // 分享到新浪微博
    private Button mShareTencentWechatButton; // 分享到微信
    private Button mShareTencentWechatMomentsButton; // 分享到朋友圈
    private Button mShareTencentQQButton; // 分享到QQ
    private Button mShareTencentQQZoneButton; // 分享到QQ空间
    private Button mShareTencentWeiboButton; // 分享到QQ微博

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

        mShareSinaWeiboButton.setOnClickListener(mListener);
        mShareTencentWechatButton.setOnClickListener(mListener);
        mShareTencentWechatMomentsButton.setOnClickListener(mListener);
        mShareTencentQQButton.setOnClickListener(mListener);
        mShareTencentQQZoneButton.setOnClickListener(mListener);
        mShareTencentWeiboButton.setOnClickListener(mListener);
    }

    View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mShareMessageEditText.getText().toString();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
