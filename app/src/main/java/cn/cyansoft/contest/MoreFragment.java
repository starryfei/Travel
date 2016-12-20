package cn.cyansoft.contest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.QQ.AboutUsActivity;
import cn.cyansoft.contest.QQ.BuceaApplication;
import cn.cyansoft.contest.QQ.FeedBackActivity;
import cn.cyansoft.contest.QQ.ImpressionEntity;
import cn.cyansoft.contest.QQ.ImpressionList;
import cn.cyansoft.contest.QQ.LogUtil;
import cn.cyansoft.contest.QQ.LoginActivity;
import cn.cyansoft.contest.QQ.MyImageLoader;
import cn.cyansoft.contest.QQ.PreferenceUitl;
import cn.cyansoft.contest.QQ.QQLoginHelper;
import cn.cyansoft.contest.QQ.QQUserInfoEntity;
import cn.cyansoft.contest.QQ.UserCenterAdapter;
import cn.cyansoft.contest.QQ.UserItem;
import cn.cyansoft.contest.QQ.onGetUserInfoListener;
import cn.cyansoft.contest.Weibo.WeiboUserInfoEntity;
import cn.cyansoft.contest.Yingxiang.ImpressionListActivity;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.JSONUtil;

public class MoreFragment extends BaseFragment implements View.OnClickListener {
	protected static final int GET_MY_IMPRESSION_SUCCESS = 0;
	protected static final int GET_MY_LIKES_SUCCESS = 1;
	private LogUtil mLog = LogUtil.getInstance();
	private Button mBtnLogin;
	private ImageView mIvUserHeader;
	private Activity mContext;
	private ListView mLvOperations;
	private ListView mLvAboutApp;
	private TextView mUserNickname;
	private TextView mUserDesc;
	private DisplayImageOptions options=new DisplayImageOptions.Builder().showStubImage(R.drawable.qq_user_center_header_default)
			.showImageForEmptyUri(R.drawable.qq_user_center_header_default)
			.showImageOnFail(R.drawable.qq_user_center_header_default)
			.displayer(new RoundedBitmapDisplayer(10))
			.cacheInMemory(true)
			.cacheOnDisc(true)
			.build();
	private BuceaApplication mApplication;
	private TextView mBtnLogout;
	private WeiboUserInfoEntity mWeiboUserInfo;
	private QQUserInfoEntity mQQUserInfo;
	private String mLoginModeStr;
	private String mUid;
	private List<ImpressionEntity> mMyImpressionList;
	private List<ImpressionEntity> mMyLikesList;
	private TextView mTvCollectCount;
	private TextView mTvImpressionCount;
	private TextView mTvLikesCount;
	private TextView mTvReadCount;
	private PreferenceUitl mSPUtil;
	private ProgressDialog mDialog;

	private Handler mHandler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_MY_IMPRESSION_SUCCESS:
				if (mMyImpressionList.size()!=0) {
					mTvImpressionCount.setText(mMyImpressionList.size()+"");
				}
				break;
			case GET_MY_LIKES_SUCCESS:
				if (mMyLikesList.size()!=0) {
					mTvLikesCount.setText(mMyLikesList.size()+"");
				}
				break;
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.qq_bucea_more_fragment, null);
		initView(view);
		initData();
		initEvent();
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
		((MainActivity)activity).setGetUserInfoListener(new onGetUserInfoListener() {

			@Override
			public void getWeiboUserInfo(WeiboUserInfoEntity weiboUserInfo) {
				showWeiboUserInfo(weiboUserInfo);
			}

			
			@Override
			public void getQQUserInfo(QQUserInfoEntity qqUserInfo) {
				showQQUserInfo(qqUserInfo);
			}

		});
	}
	
	private void initView(View view) {
		mBtnLogin = (Button) view.findViewById(R.id.user_login_btn);
		mUserNickname = (TextView) view.findViewById(R.id.tv_user_nick_name);
		mUserDesc = (TextView) view.findViewById(R.id.tv_user_description);
		mIvUserHeader = (ImageView) view.findViewById(R.id.iv_user_header);
		mLvOperations = (ListView) view.findViewById(R.id.lv_my_operations);
		mLvAboutApp = (ListView) view.findViewById(R.id.lv_about_app);
		mBtnLogout = (TextView) view.findViewById(R.id.tv_logout);
		
		mTvCollectCount = (TextView) view.findViewById(R.id.tv_my_collect_count);
		mTvImpressionCount = (TextView) view.findViewById(R.id.tv_my_impression_count);
		mTvLikesCount = (TextView) view.findViewById(R.id.tv_my_likes_count);
		mTvReadCount = (TextView) view.findViewById(R.id.tv_my_read_count);
		
	}

	@Override
	protected void initData() {
		mDialog = new ProgressDialog(mContext);
		mSPUtil = PreferenceUitl.getInstance(mContext);
		mLoginModeStr = mSPUtil.getString(Constants.LOGIN_MODE, "");
		if (!TextUtils.isEmpty(mLoginModeStr)) {
			
			showLoginedView();
			
			if (mLoginModeStr.equals(Constants.LOGINMODE_QQ)) {
				String qqHeadUrl = mSPUtil.getString(Constants.QQParams.QQ_HEAD_URL, "");
				String qqNickName = mSPUtil.getString(Constants.QQParams.QQ_NICK_NAME, "");
				mUid = mSPUtil.getString(Constants.QQParams.QQOPEN_ID, "");
				
				if (!TextUtils.isEmpty(qqHeadUrl)) {
					MyImageLoader.getInstance(mContext).displayImage(qqHeadUrl,mIvUserHeader,options);
				}
				if (!TextUtils.isEmpty(qqNickName)) {
					mUserNickname.setText(qqNickName);
				}
			}else if (mLoginModeStr.equals(Constants.LOGINMODE_SINA)) {
				String weiboHeadUrl = mSPUtil.getString(Constants.WeiboParams.WEIBO_HEAD_URL, "");
				String weiboNickName = mSPUtil.getString(Constants.WeiboParams.WEIBO_NICK_NAME, "");
				String weiboDesc = mSPUtil.getString(Constants.WeiboParams.WEIBO_DESCRIPTION, "");
				mUid = mSPUtil.getString(Constants.WeiboParams.WEIBO_UID, "");
				
				if (!TextUtils.isEmpty(weiboHeadUrl)) {
					MyImageLoader.getInstance(mContext).displayImage(weiboHeadUrl,mIvUserHeader,options);
				}
				if (!TextUtils.isEmpty(weiboNickName)) {
					mUserNickname.setText(weiboNickName);
				}
				if (!TextUtils.isEmpty(weiboDesc)) {
					mUserDesc.setText(weiboDesc);
				}
			}
		}else {
			mIvUserHeader.setBackgroundResource(R.drawable.qq_user_center_header_default);
		}
		
		UserItem collectItem = new UserItem();
		collectItem.setItemName("我的印象");
		UserItem readItem = new UserItem();
		readItem.setItemName("我的喜欢");
		
		UserItem aboutUsItem = new UserItem();
		aboutUsItem.setItemName("关于我们");
		UserItem feedbackUsItem = new UserItem();
		feedbackUsItem.setItemName("意见反馈");
		UserItem shareItem = new UserItem();
		shareItem.setItemName("推荐好友");
		
		List<UserItem> operationItems = new ArrayList<UserItem>();
		operationItems.add(collectItem);
		operationItems.add(readItem);
		
		List<UserItem> aboutAppItems = new ArrayList<UserItem>();
		aboutAppItems.add(aboutUsItem);
		aboutAppItems.add(feedbackUsItem);
		aboutAppItems.add(shareItem);
		
		UserCenterAdapter operationAdapter = new UserCenterAdapter(mContext, operationItems);
		mLvOperations.setAdapter(operationAdapter);
		
		UserCenterAdapter aboutAppAdapter = new UserCenterAdapter(mContext, aboutAppItems);
		mLvAboutApp.setAdapter(aboutAppAdapter);
		if (!TextUtils.isEmpty(mUid)) {
			mMyImpressionList = new ArrayList<ImpressionEntity>();
			mMyLikesList = new ArrayList<ImpressionEntity>();
			getMyData(Constants.ServerParams.GET_MY_LIKES_URL);
			getMyData(Constants.ServerParams.GET_MY_IMPRESSIONS_URL);
		}
	}
	
	/**
	 * 
	 * @Description: 获取我的照片或我喜欢的照片
	 * @param @param url 

	 * @param url
	 */
	private void getMyData(final String url) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("uid", mUid);
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				super.onSuccess(statusCode, content);
				try {
					JSONArray array = new JSONArray(content);
					ImpressionList list = JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(array.toString()), ImpressionList.class);
					if (!list.getData().isEmpty()) {
						if (url.equals(Constants.ServerParams.GET_MY_LIKES_URL) && mMyLikesList != null) {
							mMyLikesList.addAll(list.getData());
							mHandler.sendEmptyMessage(GET_MY_LIKES_SUCCESS);
						}else if (url.equals(Constants.ServerParams.GET_MY_IMPRESSIONS_URL) &&  mMyImpressionList != null) {
							mMyImpressionList.addAll(list.getData());
							mHandler.sendEmptyMessage(GET_MY_IMPRESSION_SUCCESS);
						}
					}
				} catch (JSONException e) {
					mLog.e(e);
				}catch (JsonParseException e) {
					mLog.e(e);
				} catch (JsonMappingException e) {
					mLog.e(e);
				} catch (IOException e) {
					mLog.e(e);
				} catch (Exception e) {
					mLog.e(e);
				}
			}
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
			}
		});
	}
	/***
	 * 显示微博用户信息
	 * @param weiboUserInfo
	 */
	private void showWeiboUserInfo(WeiboUserInfoEntity weiboUserInfo) {
		this.mWeiboUserInfo = weiboUserInfo;
		showLoginedView();
		mUserNickname.setText(weiboUserInfo.getName());
		mUserDesc.setText(weiboUserInfo.getDescription());
		MyImageLoader.getInstance(mContext).displayImage(weiboUserInfo.getProfile_image_url(),mIvUserHeader,options);
	}
	
	private void showLoginedView() {
		mBtnLogin.setVisibility(View.GONE);
		mUserNickname.setVisibility(View.VISIBLE);
		mUserDesc.setVisibility(View.VISIBLE);
		mBtnLogout.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 显示QQ用户信息
	 * @param qqUserInfo
	 */
	private void showQQUserInfo(QQUserInfoEntity qqUserInfo) {
		this.mQQUserInfo = qqUserInfo;
		showLoginedView();
		mUserNickname.setText(qqUserInfo.getNickname());
		MyImageLoader.getInstance(mContext).displayImage(qqUserInfo.getFigureurl_1(),mIvUserHeader,options);
	}


	@Override
	protected void initEvent() {
		mBtnLogin.setOnClickListener(this);
		mBtnLogout.setOnClickListener(this);
		mLvOperations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(mContext, ImpressionListActivity.class);
				if (position == 0) {
					intent.putExtra("impression_list", (Serializable)mMyImpressionList);
					intent.putExtra("type", "my_impression");
				}else if (position == 1){
					intent.putExtra("impression_list", (Serializable)mMyLikesList);
					intent.putExtra("type", "my_likes");
				}
				startActivity(intent);
			}
		});
		mLvAboutApp.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if (position == 0) {
					Intent intent = new Intent(mContext,AboutUsActivity.class);
					startActivity(intent);
				}else if (position == 1) {
					Intent intent = new Intent(mContext, FeedBackActivity.class);
					startActivity(intent);
				}else if (position == 2) {
					Intent share_intent = new Intent();
					share_intent.setAction(Intent.ACTION_SEND);
					share_intent.setType("text/plain");
					share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
					share_intent.putExtra(Intent.EXTRA_TEXT, "Hi，推荐你使用一款软件：e游锦州自助服务系统");
					
					//创建选择器
					share_intent = Intent.createChooser(share_intent, "分享");
					startActivity(share_intent);
				}
			}
		});
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.user_login_btn) {
				Intent intent = new Intent(mContext,LoginActivity.class);
				mContext.startActivityForResult(intent, 100);
		}else if (view.getId() == R.id.tv_logout ) {
			showLogoutDialog();
		}
	}
	
	/**
	 * 显示退出当前应用的Dialog
	 */
	private void showLogoutDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("提示");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setMessage("退出登录后将无法使用照片上传与分享功能，是否仍要退出？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getActivity(),MainActivity.class);
				startActivity(intent);
				final String loginMode = mSPUtil.getString(Constants.LOGIN_MODE, "");
//				//微博登录
//				if (!TextUtils.isEmpty(loginMode) && loginMode.equals(Constants.LOGINMODE_SINA)) {
//					String token = mSPUtil.getString(Constants.WeiboParams.WEIBO_ACCESS_TOKEN, "");
//					if (TextUtils.isEmpty(token)) {
//						return;
//					}
//					mDialog.setMessage("正在注销，请稍候...");
//					mDialog.show();
//					WeiboLoginHelper.getInstance().logout(mContext, token, new WeiboLoginHelper.WeiboLogoutListener() {
//
//						@Override
//						public void onSuccess(String response) {
//							mDialog.dismiss();
//							clearDataInSp(loginMode);
//							showLogoutView();
//						}
//
//						@Override
//						public void onFailure(Throwable thr) {
//							mDialog.dismiss();
//							Toast.makeText(mContext, "注销失败，请重试!", Toast.LENGTH_LONG);
//						}
//					});
//				}else
				if (!TextUtils.isEmpty(loginMode) && loginMode.equals(Constants.LOGINMODE_QQ)) {
					QQLoginHelper.getInstance().qqLogout();
					clearDataInSp(loginMode);
					showLogoutView();
				}
			}
		}).setNegativeButton("取消", null);

		builder.show();

	}
	
	/**
	 * 
	 * @Title: clearDataInSp 
	 * @Description: 清除SP中的数据
	 * @param @param loginMode    设定文件 
	 * @return void    返回类型 
	 * @throws 
	 * @author 欧阳海冰（OuyangHaibing）
	 * @date 2014年6月13日 下午8:01:25
	 */
	private void clearDataInSp(String loginMode) {
		if (loginMode.equals(Constants.LOGINMODE_SINA)) {
			mSPUtil.remove(Constants.WeiboParams.WEIBO_ACCESS_TOKEN);
			mSPUtil.remove(Constants.WeiboParams.WEIBO_UID);
			mSPUtil.remove(Constants.WeiboParams.WEIBO_EXPIRES_IN);
			mSPUtil.remove(Constants.LOGIN_MODE);
			mSPUtil.remove(Constants.WeiboParams.WEIBO_HEAD_URL);
			mSPUtil.remove(Constants.WeiboParams.WEIBO_NICK_NAME);
			mSPUtil.remove(Constants.WeiboParams.WEIBO_DESCRIPTION);
		}else if (loginMode.equals(Constants.LOGINMODE_QQ)) {
			mSPUtil.remove(Constants.QQParams.QQOPEN_ID);
			mSPUtil.remove(Constants.QQParams.QQACCESS_TOKEN);
			mSPUtil.remove(Constants.LOGIN_MODE);
			mSPUtil.remove(Constants.QQParams.QQ_HEAD_URL);
			mSPUtil.remove(Constants.QQParams.QQ_NICK_NAME);
		}
	}
	
	/**
	 * 
	 * @Title: showLogoutView 
	 * @Description: 显示注销后的界面
	 * @param     //设定文件
	 * @return void    返回类型 
	 * @throws 
	 * @author 欧阳海冰（OuyangHaibing）
	 * @date 2014年6月13日 下午8:16:18
	 */
	private void showLogoutView() {
		mBtnLogin.setVisibility(View.VISIBLE);
		mUserNickname.setVisibility(View.GONE);
		mUserDesc.setVisibility(View.GONE);
		mBtnLogout.setVisibility(View.GONE);
		MyImageLoader.getInstance(mContext).displayImage("drawable://"+R.drawable.qq_user_center_header_default,mIvUserHeader,options);
	}

}
