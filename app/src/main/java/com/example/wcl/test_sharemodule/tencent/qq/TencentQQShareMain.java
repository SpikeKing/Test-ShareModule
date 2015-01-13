package com.example.wcl.test_sharemodule.tencent.qq;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

/**
 * 腾讯分享的主函数
 * <p/>
 * Created by C.L.Wang on 15-1-13.
 */
public class TencentQQShareMain {

    public static Tencent sTencent; // 腾讯QQ的静态实例
    private Activity mActivity; // Activity活动

    @SuppressWarnings("unused")
    public TencentQQShareMain(Activity activity) {
        mActivity = activity;
        sTencent = Tencent.createInstance("1101774620", activity);
    }

    @SuppressWarnings("unused")
    public void sendMessageByQQ(String msg) {

        int extarFlag = 0x0;
        int shareType = QQShare.SHARE_TO_QQ_TYPE_APP;
        final Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测试"); //标题
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://3g.baidu.com/"); // 需要跳转的链接
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, msg); // 信息
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"); // 链接的配图
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "春雨医生"); // APP的名称
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, shareType); // 分享的类型
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, extarFlag);

        if ((extarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN) != 0) {
            Toast.makeText(mActivity, "在好友选择列表会自动打开分享到qzone的弹窗~~~", Toast.LENGTH_SHORT).show();
        } else if ((extarFlag & QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE) != 0) {
            Toast.makeText(mActivity, "在好友选择列表隐藏了qzone分享选项~~~", Toast.LENGTH_SHORT).show();
        }
        doShareToQQ(params);
    }

    @SuppressWarnings("unused")
    public void sendMessageByZone(String msg) {

        int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType); // 分享的类型
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "测试"); //标题
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, msg); // 信息
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://3g.baidu.com/"); // 需要跳转的链接
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add("http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        doShareToQzone(params);
    }

    private void doShareToQQ(final Bundle params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                sTencent.shareToQQ(mActivity, params, mShareListener);
            }
        }).start();
    }

    private void doShareToQzone(final Bundle params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                sTencent.shareToQzone(mActivity, params, mShareListener);
            }
        }).start();
    }

    IUiListener mShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Toast.makeText(mActivity, "onCancel: ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(mActivity,
                    "onComplete: " + response.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Toast.makeText(mActivity,
                    "onError: " + e.errorMessage, Toast.LENGTH_SHORT).show();
        }
    };

}
