package com.example.wcl.test_sharemodule.sina.weibo.tokens;

import android.app.Activity;
import android.widget.Toast;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * 新浪微博的回调函数
 * <p/>
 * Created by wangchenlong on 15-1-13.
 */
public class WeiboShareResponse extends Activity implements IWeiboHandler.Response {

    private Activity mActivity;

    public WeiboShareResponse(Activity activity) {
        mActivity = activity;
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseResponse 微博请求数据对象
     * @see {@link com.sina.weibo.sdk.api.share.IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(mActivity,
                        "微博分享成功",
                        Toast.LENGTH_LONG).show();

                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(mActivity,
                        "微博分享取消",
                        Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(mActivity,
                        "微博分享失败" + "Error Message: " + baseResponse.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
