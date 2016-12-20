package cn.cyansoft.contest.QQ;

import android.util.Log;

/**
 * 
 * 功能描述: 日志工具类
 * @author 欧阳海冰(OuyangHaibing)
 * @date 2014-3-28 下午5:35:26
 */
public class LogUtil {
	private static String TAG = "cn.cyansoft.contest";
	private static LogUtil log;

	private LogUtil() {

	}

	/**
	 * 
	 * @return
	 */
	public static LogUtil getInstance() {
		if (log == null) {
			log = new LogUtil();
		}
		return log;
	}

	private void debug(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.d(TAG, info);
	}

	private void error(Exception exception) {
		try {
			Log.e(TAG, "", exception);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void error(Object obj) {
		try {
			if (obj != null) {
				String funName = getFunName();
				String info = (funName == null ? obj.toString() : (funName
						+ ":" + obj.toString()));
				Log.e(TAG, info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFunName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			return "[" + Thread.currentThread().getId() + ":"
					+ st.getFileName() + ":" + st.getLineNumber() + "]";
		}
		return null;
	}

	private synchronized void verbose(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.v(TAG, info);
	}

	private synchronized void warn(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.w(TAG, info);
	}

	public synchronized void d(Object obj) {
		debug(obj);
	}

	public synchronized void e(Exception exception) {
		error(exception);
	}

	public synchronized void e(Object obj) {
		error(obj);
	}

	public synchronized void i(Object obj) {
		String funName = getFunName();
		String info = (funName == null ? obj.toString() : (funName + ":" + obj
				.toString()));
		Log.i(TAG, info);
	}

	public synchronized void v(Object obj) {
		verbose(obj);
	}

	public synchronized void w(Object obj) {
		warn(obj);
	}

}
