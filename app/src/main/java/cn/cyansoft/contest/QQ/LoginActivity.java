package cn.cyansoft.contest.QQ;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.sso.SsoHandler;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.MainActivity;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.ToastUtil;
import cn.cyansoft.contest.Weibo.WeiboLoginHelper;
import cn.cyansoft.contest.Weibo.WeiboUserInfoEntity;

public class LoginActivity extends BaseActivity {
	
	private static final int LOGIN_FRAGMENT_TAG = 0;
	private FragmentManager mFragmentManager;
	private int mCurrentF;//标记当前展示的Fragments
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.qq_bucea_login_main);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		
	}

	@Override
	protected void initData() {
		mFragmentManager = getSupportFragmentManager();
		mFragmentManager.beginTransaction().replace(
				R.id.fl_login_root, new LoginFragment(),
				Constants.FragmentTag.LOGIN_FRAGMENT_TAG)
				.commit();
		mCurrentF = LOGIN_FRAGMENT_TAG;
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 传递微博登录返回的用户信息数据
	 * @param weiboUserInfo
	 */
	public void passWeiboData(WeiboUserInfoEntity weiboUserInfo) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("weiboUserInfo", weiboUserInfo);
		Intent data = new Intent();
		data.putExtras(bundle);
		LoginActivity.this.setResult(Constants.WEIBORESULTCODE, data);
		this.finish();
	}
	/**
	 * 传递微博登录返回的用户信息数据
	 * @param //weiboUserInfo
	 */
	public void passQQData(QQUserInfoEntity qqUserInfo) {
//		Toast toast = Toast.makeText(LoginActivity.this,"请勿操作，稍等片刻",Toast.LENGTH_SHORT);
//		toast.show();
		Bundle bundle = new Bundle();
		bundle.putSerializable("qqUserInfo", qqUserInfo);
		Intent data = new Intent();
		data.putExtras(bundle);
		LoginActivity.this.setResult(Constants.QQRESULTCODE, data);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		}, 100);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		WeiboLoginHelper weiboLoginHelper = WeiboLoginHelper.getInstance();
		SsoHandler ssoHandler = weiboLoginHelper.getSsoHandler();
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}
