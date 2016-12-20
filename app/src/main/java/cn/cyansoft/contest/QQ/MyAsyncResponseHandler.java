package cn.cyansoft.contest.QQ;

import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;




/**
 * 功能描述: TODO(这里用一句话描述这个类的作用) 
 * @author 欧阳海冰(OuyangHaibing) 
 * @date 2014-3-4 下午4:07:57 
 *
 */
public class MyAsyncResponseHandler extends AsyncHttpResponseHandler {

	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		super.onFinish();
	}
	
	@Override
	public void onFailure(int statusCode, Throwable error, String content) {
		super.onFailure(statusCode, error, content);
	}
	
	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		// TODO Auto-generated method stub
		super.onFailure(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void onFailure(int statusCode, Header[] headers, Throwable error,
			String content) {
		// TODO Auto-generated method stub
		super.onFailure(statusCode, headers, error, content);
	}
	
	@Override
	public void onFailure(Throwable error, String content) {
		super.onFailure(error, content);
	}
	
	@Override
	public void onProgress(int bytesWritten, int totalSize) {
		// TODO Auto-generated method stub
		super.onProgress(bytesWritten, totalSize);
	}
	
	@Override
	public void onRetry() {
		super.onRetry();
	}
	
	@Override
	public void onFailure(Throwable error) {
		// TODO Auto-generated method stub
		super.onFailure(error);
	}
	
	@Override
	public void onSuccess(int statusCode, String content) {
		super.onSuccess(statusCode, content);
	}
	
	@Override
	public void onSuccess(String content) {
		super.onSuccess(content);
	}
}
