package cn.cyansoft.contest.QQ;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.SystemUtil;

public class FeedBackActivity extends BaseActivity {

	private LogUtil mLog = LogUtil.getInstance();
	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private EditText mEtContents;
	private EditText mEtContacts;
	private TextView mTvSubmit;
	private ProgressDialog mDialog;
	private PreferenceUitl mPreferenceUitl;
	private String mLoginModeStr;
	private TextView mTvVersion;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.qq_bucea_feed_back);
		initView();
		initData();
		initEvent();
	}
	
	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.feed_back_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mTvTitle.setText("意见反馈");
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mExtension.setClickable(false);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		mIvExtension.setVisibility(View.GONE);
		mEtContents = (EditText) findViewById(R.id.et_contents);
		mEtContacts = (EditText) findViewById(R.id.et_contacts);
		mTvSubmit = (TextView) findViewById(R.id.tv_submit);
		mTvVersion = (TextView) findViewById(R.id.tv_current_version);
	}

	@Override
	protected void initData() {
		mDialog = new ProgressDialog(FeedBackActivity.this);
		mDialog.setMessage("正在提交，请稍候...");
		mPreferenceUitl = PreferenceUitl.getInstance(FeedBackActivity.this);
		String version = getVersion();
		mTvVersion.setText(version);
	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FeedBackActivity.this.finish();
			}
		});
		
		mTvSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				doSubmit();
			}
		});
	}

	protected void doSubmit() {
		String contents = mEtContents.getText().toString();
		String contacts = mEtContacts.getText().toString();
		if (TextUtils.isEmpty(contents)) {
			Toast.makeText(FeedBackActivity.this, "建议内容不能为空，请输入！", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(contacts)) {
			Toast.makeText(FeedBackActivity.this, "联系方式不能为空，请输入！", Toast.LENGTH_LONG).show();
			return;
		}
		mLoginModeStr = mPreferenceUitl.getString(Constants.LOGIN_MODE, "");
		String nickname = null;
		if (!TextUtils.isEmpty(mLoginModeStr)) {
			if (mLoginModeStr.equals(Constants.LOGINMODE_QQ)) {
				nickname = mPreferenceUitl.getString(Constants.QQParams.QQ_NICK_NAME, "");
			}else if (mLoginModeStr.equals(Constants.LOGINMODE_SINA)) {
				nickname = mPreferenceUitl.getString(Constants.WeiboParams.WEIBO_NICK_NAME, "");
			}
		}
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.ADD_FEEDBACK_URL;
		RequestParams params = new RequestParams();
		params.put("contents", contents);
		params.put("contacts", contacts);
		if (nickname != null && !"".equals(nickname)) {
			params.put("nickname", nickname);
		}
		String phoneNumber = SystemUtil.getNativePhoneNumber(FeedBackActivity.this);
		if (phoneNumber != null && !"".equals(phoneNumber)) {
			params.put("phone", phoneNumber);
		}
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){


			@Override
			public void onStart() {
				super.onStart();
				mDialog.show();
				KeyBoardCancle();
			}
			
			@Override
			@Deprecated
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if (mDialog.isShowing()) {
					mDialog.dismiss();
				}
				try {
					JSONObject obj = new JSONObject(content);
					String status = obj.getString("status");
					if (status.equals("success")) {
						Toast.makeText(FeedBackActivity.this, "已收录，感谢您的反馈！", Toast.LENGTH_LONG).show();
						FeedBackActivity.this.finish();
					}
				}  catch (Exception e) {
					mLog.e(e);
				}
			}
			
			@Override
			@Deprecated
			public void onFailure(int statusCode, Throwable error,
					String content) {
				super.onFailure(statusCode, error, content);
				if (mDialog.isShowing()) {
					mDialog.dismiss();
				}
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
	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        return this.getString(R.string.version_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return this.getString(R.string.can_not_find_version_name);
	    }
	}

}
