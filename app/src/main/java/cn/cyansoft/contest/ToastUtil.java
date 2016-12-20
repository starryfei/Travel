package cn.cyansoft.contest;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void showLongToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		toast.show();
	}
}
