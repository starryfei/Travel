package cn.cyansoft.contest.Weibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.QQ.PreferenceUitl;


/**
 * 
 * 功能描述: 新浪微博登录工具类
 * 
 * @author 欧阳海冰(OuyangHaibing)
 * @date 2014-1-14 下午2:41:05
 * 
 *       修改历史:(修改人，修改时间，修改原因/内容)
 * 
 */
public class WeiboLoginHelper {

	/** 登录界面的activity */
	private Context mContext;
	/** SsoHandler对象 用于SSO授权登录 */
	private SsoHandler mSsoHandler;
	/** 微博认证实例 */
	private WeiboAuth mWeiboAuth;

	/** 发起注销请求时所需的回调接口 */
	private WeiboLogoutListener mLogoutListener;

	/** 微博认证Token，在授权成功后初始化 */
	private static Oauth2AccessToken mAccessToken;
	
	/**微博回调的接口*/
	private WeiboLoginListener mLoginListener;
	
	public SsoHandler getSsoHandler() {
		return mSsoHandler;
	}

//	public WeiboLoginHelper(Context context) {
//		this.mContext = context;
//	}
	
	private static WeiboLoginHelper weiboLoginHelper;
	private WeiboLoginHelper() {}
	public static WeiboLoginHelper getInstance(){
		if (weiboLoginHelper == null) {
			weiboLoginHelper = new WeiboLoginHelper();
		}
		return weiboLoginHelper;
	}

	/**
	 * 功能描述：保存调用端信息，创建微博授权类对象
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param
	 * @param
	 * @return void
	 * @date 2014-1-14 下午2:16:57
	 */
	public void initWeiboAuth(Context context) {
		this.mContext = context;
		mWeiboAuth = new WeiboAuth(mContext, Constants.WeiboParams.WEIBO_APP_KEY,
				Constants.WeiboParams.REDIRECT_URL, Constants.WeiboParams.SCOPE);
	}

	/**
	 * 功能描述：SSO授权登录(SSO登录失败后会默认使用Oauth2.0 Web授权登录)
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param
	 * @return void
	 * @date 2014-1-15 下午7:17:26
	 */
	public void ssoLogin(WeiboLoginListener loginListener) {
		this.mLoginListener = loginListener;
		mSsoHandler = new SsoHandler((Activity) mContext, mWeiboAuth);
		mSsoHandler.authorize(new AuthListener());
	}

	/**
	 * 功能描述：Oauth2.0 Web授权登录
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param
	 * @return void
	 * @date 2014-1-16 上午9:10:30
	 */
	public void oauthLogin(WeiboLoginListener loginListener) {
		this.mLoginListener = loginListener;
		if (mAccessToken == null || !mAccessToken.isSessionValid()) {
			mWeiboAuth.anthorize(new AuthListener());
		} else {
			String uid = mAccessToken.getUid();
			String token = mAccessToken.getToken();
		}
	}

	/**
	 * 功能描述: 使用Code+app_secret的方式登录
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param  
	 * @return void
	 * @throws
	 */
	public void authCodeLogin(WeiboLoginListener loginListener) {
		this.mLoginListener = loginListener;
		if (mAccessToken == null || !mAccessToken.isSessionValid()) {
			mWeiboAuth.authorize(new AuthListener(), WeiboAuth.OBTAIN_AUTH_CODE);
		}else{
			String uid = mAccessToken.getUid();
			String token = mAccessToken.getToken();
			if (loginListener != null) {
				loginListener.onLoginComplete(mAccessToken);
			}
		}
	}

	/**
	 * 功能描述: 微博认证授权回调类。 1.SSO 授权时，需要在 {@link #} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后，该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-1-14 上午9:21:44
	 * 
	 *       修改历史:(修改人，修改时间，修改原因/内容)
	 * 
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			/**
			 * Bundle[{uid=1998418174, 
			 * 	com.sina.weibo.intent.extra.USER_ICON=[B@41d6e228, _weibo_appPackage=com.sina.weibo, 
			 * 	scope=follow_app_official_microblog, 
			 * 	com.sina.weibo.intent.extra.NICK_NAME=海鴎Beyond,
			 *  remind_in=157678853,
			 *   userName=海鴎Beyond,
			 *    expires_in=157678853, 
			 *    _weibo_transaction=1399429814613,
			 *     access_token=2.00MrJPLC0cFjeE24e5e6c67bTZsfAB
			 *     }]
			 */
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
//				AccessTokenKeeper.writeWeiboAccessToken(mContext, mAccessToken);
				PreferenceUitl.getInstance(mContext).saveString(Constants.WeiboParams.WEIBO_ACCESS_TOKEN, mAccessToken.getToken());
				PreferenceUitl.getInstance(mContext).saveString(Constants.WeiboParams.WEIBO_UID, mAccessToken.getUid());
				PreferenceUitl.getInstance(mContext).saveLong(Constants.WeiboParams.WEIBO_EXPIRES_IN, mAccessToken.getExpiresTime());
				PreferenceUitl.getInstance(mContext).saveString(Constants.LOGIN_MODE, Constants.LOGINMODE_SINA);
				if (mLoginListener != null) {
					mLoginListener.onLoginComplete(mAccessToken);
				}
			}else {
				// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
                String code = values.getString("code");
                String message = "授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(mContext, "授权失败!", Toast.LENGTH_SHORT).show();
			if (mLoginListener != null) {
				mLoginListener.onLoginError();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(mContext, "授权取消!", Toast.LENGTH_SHORT).show();
			if (mLoginListener != null) {
				mLoginListener.onLoginCancel();
			}
		}
	}

	/**
	 * 异步获取 Token。
	 * 
	 * @param authCode
	 *            授权 Code，该 Code 是一次性的，只能被获取一次 Token
	 * @param appSecret
	 *            应用程序的 APP_SECRET
	 */
//	public void fetchTokenAsync(String authCode, String appSecret) {
//		RequestParams params = new RequestParams();
//		params.put(WBConstants.AUTH_PARAMS_CLIENT_ID, WeiboLoginParams.APP_KEY);
//		params.put(WBConstants.AUTH_PARAMS_CLIENT_SECRET, appSecret);
//		params.put(WBConstants.AUTH_PARAMS_GRANT_TYPE, "authorization_code");
//		params.put(WBConstants.AUTH_PARAMS_CODE, authCode);
//		params.put(WBConstants.AUTH_PARAMS_REDIRECT_URL,
//				WeiboLoginParams.REDIRECT_URL);
//
//		/**
//		 * 请求网络
//		 */
//		new MyHttpClient(mContext).post(WeiboLoginParams.OAUTH2_ACCESS_TOKEN_URL, params,
//				new MyAsyncResponseHandler() {
//					@Override
//					public void onStart() {
//						super.onStart();
//					}
//
//					@Override
//					public void onSuccess(int statusCode, String content) {
//						super.onSuccess(statusCode, content);
//						Log.d("-------------->>>", "Response: " + content);
//						Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(content);
//						if (accessToken != null && accessToken.isSessionValid()) {
//							mAccessToken = accessToken;
//							String token = accessToken.getToken();
//							String uid = accessToken.getUid();
//							Log.d("-------------->>>", "token:"+token+",uid:"+uid);
//						} else {
//							Log.d("-------------->>>","Failed to receive access token");
//							if (mLoginListener != null) {
//								mLoginListener.onLoginError();
//							}
//						}
//					}
//
//					@Override
//					public void onFailure(int statusCode, Throwable error,
//							String content) {
//						super.onFailure(statusCode, error, content);
//						if (mLoginListener != null) {
//							mLoginListener.onLoginError();
//						}
//					}
//				});
//
//	}
	
	/**
	 * 功能描述：登录注销
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param
	 * @return void
	 * @date 2014-1-16 上午9:17:53
	 */
	public void logout(Context context, String accessToken,final WeiboLogoutListener logoutListener) {
		mAccessToken = null;
		this.mLogoutListener = logoutListener;
		if (accessToken == null && "".equals(accessToken)) {
			return;
		}
		RequestParams params = new RequestParams();
		params.put("access_token", accessToken);
		AsyncHttpClient client = new AsyncHttpClient();
		// 请求weibo服务器注销
		client.post(Constants.WeiboParams.REVOKE_OAUTH_URL, params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				if (mLogoutListener != null) {
					mLogoutListener.onSuccess(content);
				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
				if (mLogoutListener != null) {
					mLogoutListener.onFailure(error);
				}
			}
		});
		

	}

	/**
	 * 功能描述: 发起注销请求时所需的回调接口
	 * 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-1-20 下午12:02:26
	 * 
	 *       修改历史:(修改人，修改时间，修改原因/内容)
	 * 
	 */
	public interface WeiboLogoutListener {

		/**
		 * 当注销成功时，该函数被调用。
		 * 
		 * @param response
		 *            服务器返回的字符串
		 */
		public void onSuccess(String response);

		/**
		 * 当注销失败时，该函数被调用。
		 * 
		 * @param thr
		 * @param
		 */
		public void onFailure(Throwable thr);
	}

	
	/**
	 * 功能描述: 微博登录回调接口 
	 * @author 欧阳海冰(OuyangHaibing) 
	 * @date 2014-2-14 上午9:41:32 
	 *
	 */
	public interface WeiboLoginListener{
		/**
		 * 功能描述: 登录完成时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param @param uid
		 * @param @param token
		 * @param @param channelId 
		 * @return void
		 * @throws
		 */
		public void onLoginComplete(Oauth2AccessToken accessToken);
		
		/**
		 * 功能描述: 取消登录时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param  
		 * @return void
		 * @throws
		 */
		public void onLoginCancel();
		
		/**
		 * 功能描述: 登录异常时回调
		 * @author 欧阳海冰(OuyangHaibing)
		 * @param  
		 * @return void
		 * @throws
		 */
		public void onLoginError();
	}

}
