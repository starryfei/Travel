package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class SystemUtil {

	/**
	 * 
	 * 功能描述: 判断是否有网络连接 
	 * @param @param context
	 * @param @return  
	 * @return boolean    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:26:07
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 功能描述: 判断WIFI网络是否可用 
	 * @param @param context
	 * @param @return  
	 * @return boolean    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:26:40
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 功能描述: 判断MOBILE网络是否可用
	 * @param @param context
	 * @param @return  
	 * @return boolean    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:27:10
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * 功能描述: 获取联网方式
	 * @param @param ctx
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:27:40
	 */
	public static String getNetType(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			String typeName = info.getTypeName();
			return typeName;
		}
		return null;
	}
	
	/**
	 * 
	 * 功能描述: 获取屏幕分辨率--高度
	 * @param @param context
	 * @param @return  
	 * @return int    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:31:29
	 */
	public static int getScreenWidthSize(Context context) {
		int screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
		if (screenWidth != 0) {
			return screenWidth;
		}
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		if (null != dm) {
			return dm.widthPixels;
		}
		return 0;
	}

	/**
	 * 
	 * 功能描述: 获取屏幕分辨率--宽度
	 * @param @param context
	 * @param @return  
	 * @return int    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:31:10
	 */
	public static int getScreenHeightSize(Context context) {
		int screenHeight = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getHeight();
		if (screenHeight != 0) {
			return screenHeight;
		}
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		if (null != dm) {
			return dm.heightPixels;
		}
		return 0;
	}
	
	/**
	 * 功能描述: 获取用户ip
	 * @author 欧阳海冰(OuyangHaibing)
	 * @param @return 
	 * @return String
	 * @throws
	 */
	public static String getUserIp(Context context) {
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int i = wifiInfo.getIpAddress();
		return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "."
				+ ((i >> 8) & 0xFF) + "." + (i & 0xFF);

	}
	
	/**
	 * 
	 * 功能描述: 获取本机手机号
	 * @param @return  
	 * @return String    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:30:20
	 */
	public static String getNativePhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String num = telephonyManager.getLine1Number();
		if (null == num && "".equals(num)) {
			num = "";
		}
		return num;
	}
}
