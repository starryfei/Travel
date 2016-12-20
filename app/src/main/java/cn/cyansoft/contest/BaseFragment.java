package cn.cyansoft.contest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.widget.Toast;

/**
 * 功能描述：Fragment基类，，提供基础复用方法，管理统一性Action
 */
public abstract class BaseFragment extends Fragment {

	/**
	 * 功能描述：初始化数据Constants
	 */
	protected abstract void initData();
	
	/**
	 * 功能描述：初始化各个响应事件
	 */
	protected abstract void initEvent();
	
	/**
	 * 功能描述：短Toast，参数为资源id
	 */
	protected void showShortToast(Context context,int res) {
		Toast.makeText(context, res, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 功能描述：短Toast，参数为String
	 */
	protected void showShortToast(Context context,String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 功能描述：长Toast，参数为资源id
	 */
	protected void showLongToast(Context context,int res) {
		Toast.makeText(context, res, Toast.LENGTH_LONG).show();
	}

	/**
	 * 功能描述：长Toast，参数为String
	 */
	protected void showLongToast(Context context,String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}



}
