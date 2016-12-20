package cn.cyansoft.contest.QQ;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.cyansoft.contest.Constants;


/**
 * 
 * 功能描述: QQ第三方登陆工具类
 * @author 欧阳海冰(OuyangHaibing) 
 * @date 2014-3-7 上午9:57:22 
 *
 */
public class QQLoginHelper {
	private Context mContext;
	private Tencent mTencent;
	private QQAuth mQQAuth;
	private QQLoginListener mQqListener;
	
//	public QQLoginHelper(Context context){
//		this.mContext = context;
//	}
	private static QQLoginHelper qqLoginHelper;
	private QQLoginHelper() {}
	public static QQLoginHelper getInstance(){
		if (qqLoginHelper == null) {
			qqLoginHelper = new QQLoginHelper();
		}
		return qqLoginHelper;
	}
	public void initTencent(Context context){
		this.mContext = context;
		mQQAuth = QQAuth.createInstance(Constants.QQParams.APP_ID, mContext);
		mTencent = Tencent.createInstance(Constants.QQParams.APP_ID, mContext);
	}
	
	/**
	 * 
	 * 功能描述：QQ登录
	 *
	 * @param @param activity
	 * @param @param isFinish
	 * @param @param mgpLoginListener
	 * @return void
	 * @date 2014-1-16 下午5:12:59
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void qqLogin(final QQLoginListener qListener){
		this.mQqListener = qListener;
		if (!mQQAuth.isSessionValid()) {
			BaseUiListener listener = new BaseUiListener();
			mTencent.login((Activity)mContext, Constants.QQParams.SCOPE, listener );
		}else{
			String uid = mTencent.getOpenId();
			String token = mTencent.getAccessToken();
			if(mQqListener != null){
				mQqListener.onLoginComplete(token, uid);
			}else{
//				log.e("qListener is null");
			}
		}
	}
	
	
	/**
	 * 功能描述：注销QQ登陆
	 *
	 * @param 
	 * @return void
	 */
	public void qqLogout() {
		mTencent.logout((Activity)mContext);
	}

	/**
	 * 
	 * 功能描述： 实现回调 IUiListener 接口：调用SDK已经封装好的接口时，例如：登录、快速支付登录、应用分享、应用邀请等接口。
	 *
	 * @date 2014-1-13
	 */
	private class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {
			Log.i("------->>>>>>>onCancel", "取消授权");
			if(mQqListener != null){
				mQqListener.onLoginCancel();
			}
		}


		@Override
		public void onError(UiError e) {
			Log.i("------->>>>>>>onError", "授权失败");
			if(mQqListener != null){
				mQqListener.onLoginError();
			}
		}

		@Override
		public void onComplete(Object response) {
			JSONObject jsonObject = (JSONObject) response;
			try {
				String openId = jsonObject.getString(Constants.QQParams.QQOPEN_ID);
				String token = jsonObject.getString(Constants.QQParams.QQACCESS_TOKEN);
				String expires_in = jsonObject.getString(Constants.QQParams.QQEXPIRES_IN);
				PreferenceUitl.getInstance(mContext).saveString(Constants.QQParams.QQOPEN_ID, openId);
				PreferenceUitl.getInstance(mContext).saveString(Constants.QQParams.QQACCESS_TOKEN, token);
				PreferenceUitl.getInstance(mContext).saveString(Constants.QQParams.QQEXPIRES_IN, expires_in);
				PreferenceUitl.getInstance(mContext).saveString(Constants.LOGIN_MODE, Constants.LOGINMODE_QQ);
				/**
				 * oauth_consumer_key=100330589&
				 * access_token=A1D81DA65A95EEB4CB67390385F6D31A&
				 * openid=8B96344BCBF91B31B55F8138D17B6907&
				 * format=json
				 */
//				getOpenid(token, uid);
				if(mQqListener != null){
					mQqListener.onLoginComplete(openId,token);
				}else{
//					log.e("qListener is null");
				}
			} catch (JSONException e) {
//				log.e(e);
				e.printStackTrace();
			}
		}
	}
	
	

	/**
	 * 功能描述：QQ登录回调，通知调用方根据不同登录结果进行不同逻辑操作
	 */
	public interface QQLoginListener {
		//登录完成
		public void onLoginComplete(String openid, String token);
		//取消登录
		public void onLoginCancel();
		//登录异常‘
		public void onLoginError();
	}
}
