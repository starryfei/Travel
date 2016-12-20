package cn.cyansoft.contest.Yingxiang;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;


/**
 * 
 * 功能描述: QQ分享工具类
 * @author 欧阳海冰(OuyangHaibing) 
 * @date 2014-3-7 上午9:52:47 
 *
 */
public class QQShareHelper {

	private int shareType = QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
	private Context mContext;
	private Tencent mTencent;
	/**单例模式*/
	private static QQShareHelper mQQShareUtil;
	private QQShareHelper(){}
	public static synchronized QQShareHelper getInstance(){
		if (mQQShareUtil == null) {
			mQQShareUtil = new QQShareHelper();
		}
		return mQQShareUtil;
	}
	
	public void init(Context context) {
		this.mContext = context;
		mTencent = Tencent.createInstance(Constants.QQParams.APP_ID, mContext);
//		mTencent.setAccessToken(AccessTokenKeeper.readQQOpenId(mContext), 
//				AccessTokenKeeper.readQQExpiresIn(mContext));
//		mTencent.setOpenId(AccessTokenKeeper.readQQOpenId(mContext));
	}
	
	/**
	 * 
	 * 功能描述: 对外公开的分享到QQ的方法
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param content
	 * @param @param shareListener 
	 * @return void
	 * @throws
	 */
	public void doShareToQQ(String content,String picPath,OnQQShareListener shareListener) {
		Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, shareType);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "e游锦州服务系统");
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, mContext.getString(R.string.my_impression_qq_share_target_url));
        ArrayList<String> urlList = new ArrayList<String>();
        urlList.add(picPath);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, urlList);
        doShareToQzone(params,shareListener);
	}
	
	/**
	 * 
	 * 功能描述: 用异步方式启动分享
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @param params
	 * @param @param shareListener 
	 * @return void
	 * @throws
	 */
	private void doShareToQzone(final Bundle params,final OnQQShareListener shareListener) {
		final Activity activity = (Activity) mContext;
		new Thread(new Runnable() {
	            
	            @Override
	            public void run() {
	            	mTencent.shareToQzone(activity, params, new IUiListener() {
	            		
	            		@Override
	            		public void onComplete(Object response) {
	            			if (shareListener != null) {
	            				shareListener.onComplete(response);
	        				}
	            		}

	                    @Override
	                    public void onCancel() {
	                    	if (shareListener != null) {
	                    		shareListener.onCancel();
	        				}
	                    }

	                    @Override
	                    public void onError(UiError e) {
	                    	if (shareListener != null) {
	                    		shareListener.onError(e);
	        				}
	                    }
	                });
	            }
	        }).start();
	}
	
	/**
	 * 
	 * 功能描述: 微博分享状态回调接口
	 * @author 欧阳海冰(OuyangHaibing) 
	 * @date 2014-3-6 下午2:47:13 
	 *
	 */
	public interface OnQQShareListener {
		/**
		 * 
		 * 功能描述: 分享成功时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param @param statusCode
		 * @param @param content 
		 * @return void
		 * @throws
		 */
		public void onComplete(Object response);
		
		/**
		 * 
		 * 功能描述: 分享取消时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param @param error
		 * @param @param content 
		 * @return void
		 * @throws
		 */
		public void onCancel();
		
		/**
		 * 功能描述: 分享失败时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param @param e 
		 * @return void
		 * @throws
		 */
		public void onError(UiError e);
	}
}
