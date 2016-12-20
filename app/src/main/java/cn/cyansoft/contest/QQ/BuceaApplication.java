package cn.cyansoft.contest.QQ;


import android.app.Application;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
//import com.baidu.mapapi.MKGeneralListener;
//import com.baidu.mapapi.map.MKEvent;
//
//import cn.cyansoft.contest.Constants;

public class BuceaApplication extends Application {

	private static BuceaApplication mInstance = null;
	public BMapManager mBMapManager;
	public boolean m_bKeyRight = true;
//	private WeiboUserInfoEntity weiboUserInfo;
//	private QQUserInfoEntity qqUserInfo;
	
	public static BuceaApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
//		initBMapManager(this);
	}
	
//	public void saveWeiboUserInfo(WeiboUserInfoEntity weiboUserInfo) {
//		this.weiboUserInfo = weiboUserInfo;
//	}
//	
//	public WeiboUserInfoEntity getWeiboUserInfo() {
//		return weiboUserInfo;
//	}
//	
//	public QQUserInfoEntity getQqUserInfo() {
//		return qqUserInfo;
//	}
//
//	public void setQqUserInfo(QQUserInfoEntity qqUserInfo) {
//		this.qqUserInfo = qqUserInfo;
//	}

//	public void initBMapManager(Application context) {
//        if (mBMapManager == null) {
//            mBMapManager = new BMapManager(context);
//        }
//
//        if (!mBMapManager.init(Constants.BAIDU_MAP_KEY,new MyGeneralListener())) {
//            Toast.makeText(BuceaApplication.getInstance().getApplicationContext(),
//					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
//        }
//	}

//	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
//	public static class MyGeneralListener implements MKGeneralListener {
//
//		@Override
//		public void onGetNetworkState(int iError) {
//			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
//				Toast.makeText(
//						BuceaApplication.getInstance().getApplicationContext(),
//						"您的网络出错啦！", Toast.LENGTH_LONG).show();
//			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
//				Toast.makeText(
//						BuceaApplication.getInstance().getApplicationContext(),
//						"输入正确的检索条件！", Toast.LENGTH_LONG).show();
//			}
//			// ...
//		}
//
//		@Override
//		public void onGetPermissionState(int iError) {
//			// 非零值表示key验证未通过
//			if (iError != 0) {
//				// 授权Key错误：
//					Toast.makeText(
//							BuceaApplication.getInstance().getApplicationContext(),
//							"请输入正确的授权Key,并检查您的网络连接是否正常！error: "
//									+ iError, Toast.LENGTH_LONG).show();
//					BuceaApplication.getInstance().m_bKeyRight = false;
//			} else {
//				BuceaApplication.getInstance().m_bKeyRight = true;
//				if (AppConfig.IsDebugMode) {
//					Toast.makeText(
//							BuceaApplication.getInstance().getApplicationContext(),
//							"key认证成功", Toast.LENGTH_LONG).show();
//				}
//			}
//		}
//	}
}
