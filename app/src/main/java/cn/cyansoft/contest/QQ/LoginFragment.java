package cn.cyansoft.contest.QQ;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.cyansoft.contest.BaseFragment;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.Weibo.WeiboLoginHelper;

public class LoginFragment extends BaseFragment implements View.OnClickListener {
	
//	private View mWeiboLogin;
	private View mQQLogin;
	private View mTitleBar;
	private TextView mTvTitle;
	private View mBack;
	private View mExtension;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.qq_bucea_login_fragment, null);
		initView(view);
		initData();
		initEvent();
		return view;
	}

	private void initView(View view) {
//		mWeiboLogin = view.findViewById(R.id.ll_weibo_login);
		mQQLogin = view.findViewById(R.id.ll_qq_login);
		
		mTitleBar = view.findViewById(R.id.title_bar);
		mBack = mTitleBar.findViewById(R.id.head_back);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mExtension.setClickable(false);

	}

	@Override
	protected void initData() {
		mTvTitle.setText("第三方登陆");
		mExtension.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void initEvent() {
//		mWeiboLogin.setOnClickListener(this);
		mQQLogin.setOnClickListener(this);
		mBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
//		//微博登录
//		if (view == mWeiboLogin) {
//			WeiboLoginHelper weiboLoginHelper = WeiboLoginHelper.getInstance();
//			weiboLoginHelper.initWeiboAuth(getActivity());
//			weiboLoginHelper.ssoLogin(new WeiboLoginHelper.WeiboLoginListener() {
//
//				@Override
//				public void onLoginError() {
//
//				}
//
//				@Override
//				public void onLoginComplete(Oauth2AccessToken accessToken) {
//					getWeiboUserInfo(accessToken);
//
//				}
//
//
//				@Override
//				public void onLoginCancel() {
//					// TODO Auto-generated method stub
//
//				}
//			});
//		}
		//QQ登录
//		else
		 if (view == mQQLogin) {
			QQLoginHelper qqLoginHelper = QQLoginHelper.getInstance();
			qqLoginHelper.initTencent(getActivity());
			qqLoginHelper.qqLogin(new QQLoginHelper.QQLoginListener() {
				
				@Override
				public void onLoginError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoginComplete(String appid, String token) {
					getQQUserInfo(appid,token);
				}
				
				@Override
				public void onLoginCancel() {
					// TODO Auto-generated method stub
					
				}
			});
		}else if (view == mBack) {
			getActivity().finish();
		}
	}
	
	/**
	 * 通过token、appid获取QQ用户资料
	 * @param token
	 * @param appid
	 */
	private void getQQUserInfo(String appid, String token) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("access_token", token);
		params.put("oauth_consumer_key", Constants.QQParams.APP_ID);
		params.put("openid", appid);
		httpClient.get(Constants.QQParams.GRAPH_USER_INFO_URL,params, new MyAsyncResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
			}
			@Override
			public void onSuccess(int statusCode,Header[] headers,
								  String content) {
				super.onSuccess(statusCode, headers, content);
				Log.i("------->>onSuccess:", content);
				try {
					JSONObject obj = new JSONObject(content);
					String nickname = obj.getString("nickname");
					String figureurl_1 = obj.getString("figureurl_1");
					QQUserInfoEntity qqUserInfoEntity = new QQUserInfoEntity();
					qqUserInfoEntity.setNickname(nickname);
					qqUserInfoEntity.setFigureurl_1(figureurl_1);
					PreferenceUitl.getInstance(getActivity()).saveString(Constants.QQParams.QQ_HEAD_URL, figureurl_1);
					PreferenceUitl.getInstance(getActivity()).saveString(Constants.QQParams.QQ_NICK_NAME, nickname);
					((LoginActivity)getActivity()).passQQData(qqUserInfoEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
				Log.i("------->>onFailure:", content);
			}
			
		});
	}
	
	/**
	 * 通过accessToken获取微博用户资料
	 * @param accessToken
	 */
	private void getWeiboUserInfo(Oauth2AccessToken accessToken) {
//		String token = accessToken.getToken();
//		String uidStr = accessToken.getUid();
//		UsersAPI usersAPI = new UsersAPI(accessToken);
//		long uid = Long.parseLong(uidStr);
//		usersAPI.show(uid, new RequestListener() {
//
//			@Override
//			public void onIOException(IOException e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onError(WeiboException e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onComplete4binary(ByteArrayOutputStream responseOS) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onComplete(String response) {
//				Log.d("------------->>", response);
//				try {
//					JSONObject obj = new JSONObject(response);
//					WeiboUserInfoEntity weiboUserInfo = new WeiboUserInfoEntity();
//					weiboUserInfo.setId(obj.getLong("id"));
//					weiboUserInfo.setName(obj.getString("name"));
//					weiboUserInfo.setDescription(obj.getString("description"));
//					weiboUserInfo.setLocation(obj.getString("location"));
//					weiboUserInfo.setProfile_image_url(obj.getString("profile_image_url"));
//					weiboUserInfo.setFollowers_count(obj.getString("followers_count"));
//					weiboUserInfo.setFriends_count(obj.getString("friends_count"));
//
//					//保存到sp
//					PreferenceUitl.getInstance(getActivity()).saveString(Constants.WeiboParams.WEIBO_HEAD_URL, obj.getString("profile_image_url"));
//					PreferenceUitl.getInstance(getActivity()).saveString(Constants.WeiboParams.WEIBO_NICK_NAME, obj.getString("name"));
//					PreferenceUitl.getInstance(getActivity()).saveString(Constants.WeiboParams.WEIBO_DESCRIPTION, obj.getString("description"));
//
//					((LoginActivity)getActivity()).passWeiboData(weiboUserInfo);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});

	}
	

}
