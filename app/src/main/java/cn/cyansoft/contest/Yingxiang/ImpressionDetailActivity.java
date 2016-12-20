package cn.cyansoft.contest.Yingxiang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.QQ.CommentEntity;
import cn.cyansoft.contest.QQ.ImpressionEntity;
import cn.cyansoft.contest.QQ.JSONUtil;
import cn.cyansoft.contest.QQ.LogUtil;
import cn.cyansoft.contest.QQ.PreferenceUitl;
import cn.cyansoft.contest.R;

public class ImpressionDetailActivity extends BaseActivity implements OnClickListener {
	protected static final int GET_COMMENT_LIST_SUCCESS = 0;
	protected static final int UPDATE_LIKES_SUCCESS = 1;
	protected static final int GET_LIKES_SUCCESS = 2;
	private LogUtil mLog = LogUtil.getInstance();
	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private ImpressionEntity mImpressionEntity;
	private ImageView mIvheader;
	private TextView mTvNickName;
	private TextView mTvLoc;
	private TextView mTvDesc;
	private ImageView mIvImage;
	private TextView mTvPublishTime;
	private DisplayImageOptions mOptions;
	private PreferenceUitl mSpUitl;
	private TextView mTvLikeCount;
	private TextView mTvCommentCount;
	private ListView mLvCommentList;
	private ImageView mIvEmotion;
	private EditText mEtInput;
	private Button mBtnPublish;
	private String mLoginModeStr;
	private String mNickName;
	private String mHeaderUrl;
	private List<CommentEntity> mCommentList;
	private CommentListAdapter mAdapter;
	private ProgressDialog mDialog;
	private boolean mIsLiked = false;
	
	private Handler mHandler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_COMMENT_LIST_SUCCESS:
				if (mCommentList.size()!=0) {
					if (mAdapter == null) {
						mAdapter = new CommentListAdapter(ImpressionDetailActivity.this,mCommentList);
						mLvCommentList.setAdapter(mAdapter);
					}else {
						mAdapter.notifyDataSetChanged();
					}
					cn.cyansoft.contest.Yingxiang.Utility.setListViewHeightBasedOnChildren(mLvCommentList);
					mTvCommentCount.setText("评论："+mCommentList.size());
				}
				break;
			case UPDATE_LIKES_SUCCESS:
				mTvLikeCount.setText((Integer) msg.obj+"人喜欢");
				mImpressionEntity.setLiked_by(mUid);
				break;
			case GET_LIKES_SUCCESS:
				mTvLikeCount.setText((Integer) msg.obj+"人喜欢");
				break;
			}
		};
	};
	private LinearLayout mLlLike;
	private ImageView mIvLike;
	
	private String mUid;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.qq_bucea_impression_detail);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.impression_detail_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		
		mIvheader = (ImageView) findViewById(R.id.iv_header);
		mTvNickName = (TextView) findViewById(R.id.tv_nickname);
		mTvLoc = (TextView)findViewById(R.id.tv_loc);
		mTvDesc = (TextView) findViewById(R.id.tv_desc);
		mIvImage = (ImageView) findViewById(R.id.iv_impression_detail);
		mTvPublishTime = (TextView)findViewById(R.id.tv_publish_time);
		
		mLlLike = (LinearLayout)findViewById(R.id.ll_like);
		mIvLike = (ImageView)findViewById(R.id.iv_like);
		mTvLikeCount = (TextView)findViewById(R.id.tv_likes_count);
		mTvCommentCount = (TextView)findViewById(R.id.tv_comment_count);
		mLvCommentList = (ListView)findViewById(R.id.lv_comments_list);
		mIvEmotion = (ImageView)findViewById(R.id.iv_emotion);
		mEtInput = (EditText)findViewById(R.id.et_comment_input);
		mBtnPublish = (Button)findViewById(R.id.btn_publish_comment);
		
		mDialog = new ProgressDialog(ImpressionDetailActivity.this);
		mDialog.setMessage("正在发布");

	}

	@Override
	protected void initData() {
		mSpUitl = PreferenceUitl.getInstance(ImpressionDetailActivity.this);
		mTvTitle.setText("印象详情");
		mIvExtension.setBackgroundResource(R.drawable.head_guide_share);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mImpressionEntity = (ImpressionEntity) bundle.get("impression_entity");
		
		this.mOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.qq_user_center_header_default)
				.showImageForEmptyUri(R.drawable.qq_user_center_header_default)
				.showImageOnFail(R.drawable.qq_user_center_header_default)
				.displayer(new RoundedBitmapDisplayer(7))
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		//设置头像
		MyImageLoader.getInstance(ImpressionDetailActivity.this).displayImage(mImpressionEntity.getHeader_pic_url(), mIvheader,mOptions);
		//设置昵称
		mTvNickName.setText(mImpressionEntity.getUsername());
		//设置位置
		mTvLoc.setText(mImpressionEntity.getLoc());
		//设置照片描述
		mTvDesc.setText(mImpressionEntity.getContent());
		//设置照片
		MyImageLoader.getInstance(ImpressionDetailActivity.this).displayImage(mImpressionEntity.getPicurl(), mIvImage);
	
		//设置照片发布时间
		mTvPublishTime.setText(mImpressionEntity.getUploadDate()+getString(R.string.impression_detail_publish_way));
		mCommentList = new ArrayList<CommentEntity>();

		//获取我的昵称
		mLoginModeStr = mSpUitl.getString(Constants.LOGIN_MODE, "");
		if (!TextUtils.isEmpty(mLoginModeStr)) {
			
			if (mLoginModeStr.equals(Constants.LOGINMODE_QQ)) {
				mHeaderUrl = mSpUitl.getString(Constants.QQParams.QQ_HEAD_URL, "");
				mNickName = mSpUitl.getString(Constants.QQParams.QQ_NICK_NAME, "");
				mUid = mSpUitl.getString(Constants.QQParams.QQOPEN_ID, "");
			}else if (mLoginModeStr.equals(Constants.LOGINMODE_SINA)) {
				mHeaderUrl = mSpUitl.getString(Constants.WeiboParams.WEIBO_HEAD_URL, "");
				mNickName = mSpUitl.getString(Constants.WeiboParams.WEIBO_NICK_NAME, "");
				mUid = mSpUitl.getString(Constants.WeiboParams.WEIBO_UID, "");
			}
		}
		//设置喜欢数
//		mTvLikeCount.setText(mImpressionEntity.getLikes()+"人喜欢");
		if (mImpressionEntity.getLiked_by() == mUid) {
			mIvLike.setBackgroundResource(R.drawable.impression_detail_like_sel);
		}else {
			mIvLike.setBackgroundResource(R.drawable.impression_detail_like_nor);
		}
		getCommentsByImpressionId();
		getLikes();
	}

	/**
	 * 
	 * @Description: 得到照片的喜欢数
	 * @param  
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月29日 下午7:57:15 
	 *
	 */
	private void getLikes() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.GET_LIKES_URL;
		RequestParams params = new RequestParams();
		params.put("impressionId", mImpressionEntity.getId());
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
					JSONObject obj = new JSONObject(content);
					int likes = obj.getInt("likes");
					Message msg = Message.obtain();
					msg.obj = likes;
					msg.what = GET_LIKES_SUCCESS;
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
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

	/**
	 * 
	 * @Description: 获取本照片的所有评论
	 * @param  
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月29日 下午3:44:40 
	 *
	 */
	private void getCommentsByImpressionId() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.GET_COMMENT_BY_IMPRESSIONID_URL;
		RequestParams params = new RequestParams();
		params.put("impressionId", mImpressionEntity.getId());
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
				mDialog.show();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				mEtInput.setText("");
				mDialog.dismiss();
				try {
					JSONArray array = new JSONArray(content);
					CommentList commentList = JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(array.toString()), CommentList.class);
					if (mCommentList != null && !commentList.getData().isEmpty()) {
						mCommentList.addAll(commentList.getData());
						mHandler.sendEmptyMessage(GET_COMMENT_LIST_SUCCESS);
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
				mDialog.dismiss();
			}
		});
	}

	@Override
	protected void initEvent() {
		mIvEmotion.setOnClickListener(this);
		mBtnPublish.setOnClickListener(this);
		mLlLike.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mIvImage.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (mBtnPublish == view) {
			doPublishComment();
		}else if (mBack == view) {
			this.finish();
		}else if (mIvImage == view) {
			Intent intent = new Intent(ImpressionDetailActivity.this, FullScreenImageActivity.class);
            intent.putExtra("image_url", mImpressionEntity.getPicurl());//非必须  
            int[] location = new int[2];  
            mIvImage.getLocationOnScreen(location);  
            intent.putExtra("locationX", location[0]);//必须  
            intent.putExtra("locationY", location[1]);//必须  

            intent.putExtra("width", mIvImage.getWidth());//必须  
            intent.putExtra("height", mIvImage.getHeight());//必须  
            startActivity(intent);  
            overridePendingTransition(0, 0); 
			
		}else if (mLlLike == view) {
			if (!mIsLiked) {
				mIvLike.setBackgroundResource(R.drawable.impression_detail_like_sel);
				mIsLiked = true;
			}else {
				mIvLike.setBackgroundResource(R.drawable.impression_detail_like_nor);
				mIsLiked = false;
			}
			updateLikes();
		}
	}

	/**
	 * 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @param  
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月29日 下午5:19:09 
	 *
	 */
	private void updateLikes() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.UPDATE_LIKES_URL;
		RequestParams params = new RequestParams();
		params.put("impressionId", mImpressionEntity.getId());
		params.put("status", mIsLiked ? "0":"1");
		params.put("uid", mUid);
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				try {
					JSONObject obj = new JSONObject(content);
					int likes = obj.getInt("likes");
					Message msg = Message.obtain();
					msg.obj = likes;
					msg.what = UPDATE_LIKES_SUCCESS;
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
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

	/**
	 * 
	 * @Description: 发表评论 
	 * @param  
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月28日 下午8:39:22 
	 *
	 */
	private void doPublishComment() {	
		String comment = mEtInput.getText().toString();
		if (TextUtils.isEmpty(comment)) {
			return;
		}
		if (TextUtils.isEmpty(mNickName)) {
			Toast.makeText(ImpressionDetailActivity.this, R.string.please_login_toast, Toast.LENGTH_LONG).show();
			return;
		}
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.ADD_COMMENT_URL;
		RequestParams params = new RequestParams();
		params.put("name", mNickName);
		params.put("header_url", mHeaderUrl);
		params.put("comment", comment);
		params.put("impressionId", mImpressionEntity.getId());
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){
			@Override
			public void onStart() {
				super.onStart();
				mDialog.show();
			}
			
			@Override
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				mEtInput.setText("");
				KeyBoardCancle();
				mDialog.dismiss();
				try {
					JSONArray array = new JSONArray(content);
					CommentList commentList = JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(array.toString()), CommentList.class);
					if (mCommentList != null && !commentList.getData().isEmpty()) {
						if (!mCommentList.isEmpty()) {
							mCommentList.clear();
						}
						mCommentList.addAll(commentList.getData());
						mHandler.sendEmptyMessage(GET_COMMENT_LIST_SUCCESS);
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
				mDialog.dismiss();
			}
		});
	}
	
	/**
	 * 
	 * @Description: 关闭软键盘
	 * @param  
	 * @return void
	 * @author 欧阳海冰
	 * @mail ouyang1738@gmail.com 
	 * @date 2014年5月29日 下午8:00:16 
	 *
	 */
	public void KeyBoardCancle() {
		View view = getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	

}
