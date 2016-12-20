package cn.cyansoft.contest.Yingxiang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.QQ.LogUtil;
import cn.cyansoft.contest.QQ.MyAsyncResponseHandler;
import cn.cyansoft.contest.QQ.PreferenceUitl;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.*;

/**
 *
 * @ClassName: MyImpressionActivity
 * @Description: 我的印象Activity
 * @author 欧阳海冰
 * @mail ouyang1738@gmail.com 
 * @date 2014年5月24日 下午7:52:28 
 *
 */
public class MyImpressionActivity extends BaseActivity implements OnClickListener{

	private Context mContext;
	private LogUtil mLog = LogUtil.getInstance();
	private PreferenceUitl mSPUtil;
	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mMyImpressionIv;
	private Bitmap mBitMap;
	private ImageView mIvExtension;
	private ImageView mSinaCoverIv;
	private ImageView mQQCoverIv;
	private View mSinaFl;
	private View mQQFl;
	private boolean mSinaLogin = false;
	private boolean mQQLogin = false;
	private TextView mDingweiTv;
	private String mPicPath;
	private EditText mShareContentEt;
	private String mLoginModeStr;
//	private String mLocation;
	private ProgressDialog mDialog;
	private String mHeaderPicUrl;
	private boolean mIsUpload2QQSuccess = false;
	private boolean mIsUpload2SinaSuccess = false;
	private boolean mIsUpload2ServerSuccess = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout. bucea_impression_share_my_picture);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.impression_title_bar);
		mBack = mTitleBar.findViewById(R.id.head_back);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);

		mMyImpressionIv = (ImageView) findViewById(R.id.iv_impression_of_mine);
		mShareContentEt = (EditText) findViewById(R.id.my_impression_share_content_et);

		mQQFl = findViewById(R.id.my_impression_qq_fl);
		mQQCoverIv = (ImageView) findViewById(R.id.my_impression_qq_cover_iv);

		mDingweiTv = (TextView) findViewById(R.id.my_impression_dingwei_info_tv);
//		mDingweiTv.setClickable(false);
	}

	@Override
	protected void initData() {
		mContext = MyImpressionActivity.this;
		mSPUtil = PreferenceUitl.getInstance(mContext);
		mDialog = new ProgressDialog(mContext);
		initImageLoader();
		mTvTitle.setText("我的印象");
		mIvExtension.setBackgroundResource(R.drawable.my_impression_publish);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mPicPath = bundle.getString("pic_path");
		if (mPicPath.length()!=0) {
			ImageLoader.getInstance().displayImage("file:///"+mPicPath, mMyImpressionIv);
		}

		//从sp中获取登录方式
		mLoginModeStr = mSPUtil.getString(Constants.LOGIN_MODE, "");
		if (!TextUtils.isEmpty(mLoginModeStr)) {
			if (mLoginModeStr.equals(Constants.LOGINMODE_QQ)) {
				mQQCoverIv.setVisibility(View.VISIBLE);
				mHeaderPicUrl = mSPUtil.getString(Constants.QQParams.QQ_HEAD_URL, "");
				mQQLogin = true;
			}else {
				mQQCoverIv.setVisibility(View.GONE);
			}
		}
	}


	private void initImageLoader() {
		// 初始化图片加载库
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
						// 图片存本地
				.cacheInMemory(true).displayer(new FadeInBitmapDisplayer(50))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY) // default
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mContext)
				.memoryCache(new UsingFreqLimitedMemoryCache(16 * 1024 * 1024))
				.defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(this);
		mQQFl.setOnClickListener(this);
		mExtension.setOnClickListener(this);
		mMyImpressionIv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == mBack) {
			this.finish();
		}else if (v == mExtension) {
			doShare();
		}else if (v == mMyImpressionIv) {
			Intent intent = new Intent(MyImpressionActivity.this, FullScreenImageActivity.class);
			intent.putExtra("image_url", "file:///"+mPicPath);//非必须
			int[] location = new int[2];
			mMyImpressionIv.getLocationOnScreen(location);
			intent.putExtra("locationX", location[0]);//必须
			intent.putExtra("locationY", location[1]);//必须

			intent.putExtra("width", mMyImpressionIv.getWidth());//必须
			intent.putExtra("height", mMyImpressionIv.getHeight());//必须
			startActivity(intent);
			overridePendingTransition(0, 0);

		}else if (v == mQQFl) {
			if (mQQLogin) {
				if (mQQCoverIv.getVisibility() == View.VISIBLE) {
					mQQCoverIv.setVisibility(View.GONE);
					mDingweiTv.setText("取消发布到QQ空间");
				}else {
					mQQCoverIv.setVisibility(View.VISIBLE);
					mDingweiTv.setText("同时发布到QQ空间");
				}
			}else {
				Toast.makeText(MyImpressionActivity.this, getString(R.string.my_impression_please_login_qq), Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 *
	 * @Description: 发布印象
	 * @param
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月26日 上午10:14:49 
	 *
	 */


	private void doShare() {
		mDialog.setMessage(getString(R.string.my_impression_upload_messsage_info));
		mDialog.show();
		String shareContent = mShareContentEt.getText().toString();
		if (TextUtils.isEmpty(shareContent)) {
			shareContent = getString(R.string.my_impression_share_info);
		}
		//1、判断是否QQ登录，并且勾选了分享到QQ空间
		if (mQQLogin && mQQCoverIv.getVisibility() == View.VISIBLE) {
			QQShareHelper qqShareHelper = QQShareHelper.getInstance();
			qqShareHelper.init(MyImpressionActivity.this);
			qqShareHelper.doShareToQQ(shareContent, mPicPath, new QQShareHelper.OnQQShareListener() {

				@Override
				public void onError(UiError e) {
					// TODO Auto-generated method stub
//					mLog.e(exception);
				}

				@Override
				public void onComplete(Object response) {
					// TODO Auto-generated method stub
					mLog.d(response);
					mIsUpload2QQSuccess = true;
					if (mIsUpload2ServerSuccess) {
						mDialog.dismiss();
						goBackToPhotoWall();
					}
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
		}
		//3、上传到服务器
		try {
			String uid = "";
			String name = "";
			if (mQQLogin) {
				uid = mSPUtil.getString(Constants.QQParams.QQOPEN_ID, "");
				name = mSPUtil.getString(Constants.QQParams.QQ_NICK_NAME, "");
			}
			File file = new File(mPicPath);
			RequestParams params = new RequestParams();
			params.put("uid", uid);
			params.put("username", name);
			params.put("content", shareContent);
			params.put("file", file);
//			params.put("loc", mLocation);
			params.put("header_pic_url", mHeaderPicUrl);
			params.put("likes", 0);

			MyHttpClient mHttpClient = new MyHttpClient(mContext);
			mHttpClient.post(Constants.ServerParams.PUBLISH_ONE_IMPRESSION_URL, params,
					new MyAsyncResponseHandler() {
						@Override
						public void onStart() {
							super.onStart();
						}

						@Override
						public void onSuccess(int statusCode, String content) {
							super.onSuccess(statusCode, content);
							try {
								JSONObject obj = new JSONObject(content);
								String status = obj.getString("status");
								if (status != null && "success".equals(status)) {
									//上传服务器成功
									mIsUpload2ServerSuccess = true;
									if (mIsUpload2QQSuccess || mIsUpload2SinaSuccess) {
										mDialog.dismiss();
										goBackToPhotoWall();
									}
								}
							} catch (JSONException e) {
								mLog.e(e);
							}
						}

						@Override
						public void onFailure(Throwable error, String content) {
							super.onFailure(error, content);
							//上传服务器失败
						}
					});
		} catch (FileNotFoundException e) {
			mLog.e(e);
		}
	}

	/**
	 *
	 * @Description: 上传印象到服务器
	 * @param @param shareContent 
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月26日 上午10:27:27 
	 *
	 * @param shareContent
	 */
	private void doUploadToServer(String shareContent) {

	}

	/**
	 *
	 * @Description: 分享到新浪微博
	 * @param @param shareContent 
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月26日 上午10:22:16 
	 *
	 * @param shareContent
	 */
//	private void doShareToWeibo(String shareContent) {
//		WeiboShareHelper weiboShareHelper = WeiboShareHelper.getInstance();
//		weiboShareHelper.init(MyImpressionActivity.this);
//		//从SP中获取token,expiresIn
//		String token = mSPUtil.getString(Constants.WeiboParams.WEIBO_ACCESS_TOKEN, "");
//		long expiresIn = mSPUtil.getLong(Constants.WeiboParams.WEIBO_EXPIRES_IN, 0);
//		if (expiresIn == 0) {
//			return;
//		}
//		Oauth2AccessToken accessToken = new Oauth2AccessToken(token, String.valueOf(expiresIn));
//		weiboShareHelper.doShareToWeibo(shareContent, mPicPath,accessToken, new OnWeiboShareListener() {
//
//			@Override
//			public void onSuccess(int statusCode, String content) {
//				// TODO Auto-generated method stub
//				mIsUpload2SinaSuccess = true;
//				if (mIsUpload2ServerSuccess) {
//					mDialog.dismiss();
//					goBackToPhotoWall();
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable error, String content) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}

	/**
	 *
	 * @Description: 发布印象到QQ空间
	 * @param @param shareContent 
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月26日 上午10:20:37 
	 *
	 * @param shareContent
	 */
	private void doShareToQZone(String shareContent) {

	}

	/**
	 *
	 * @Description: 返回照片墙
	 * @param
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月26日 下午9:30:08 
	 *
	 */
	private void goBackToPhotoWall(){
		Toast.makeText(mContext, R.string.my_impression_upload_success_info, Toast.LENGTH_LONG).show();
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//回收BitMap
		if(mBitMap != null && !mBitMap.isRecycled()){
			mBitMap.recycle();
			mBitMap = null;
		}
		System.gc();
	}

}
