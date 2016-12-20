package cn.cyansoft.contest.Yingxiang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.BaseFragment;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.QQ.ImpressionEntity;
import cn.cyansoft.contest.QQ.ImpressionList;
import cn.cyansoft.contest.QQ.LogUtil;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.JSONUtil;


public class ImpressionFragment extends BaseFragment{

	protected static final int GET_ALL_IMPRESSION_SUCCESS = 0;
	private MultiColumnPullToRefreshListView mWaterfallListView;
	private LogUtil mLog = LogUtil.getInstance();
	private ProgressDialog mDialog;
	private Activity mActivity;
	private List<ImpressionEntity> mImpressionList;
	private WaterfallAdapter mAdapter;
	private boolean isRefreshed = false;
	private Handler mHandler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case GET_ALL_IMPRESSION_SUCCESS:
					if (mImpressionList.size()!=0) {
						if (mAdapter == null) {
							mAdapter = new WaterfallAdapter(mImpressionList, mActivity);
							mWaterfallListView.setAdapter(mAdapter);
						}else {
							mAdapter.notifyDataSetChanged();
						}
					}
					break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bucea_impress_fragment, null);
		mWaterfallListView = (MultiColumnPullToRefreshListView) view.findViewById(R.id.photo_waterfall_xlv);
		initEvent();
		initData();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}


	@Override
	protected void initData() {
		mDialog = new ProgressDialog(mActivity);
		mDialog.setMessage("正在请求数据，请稍候...");
		mImpressionList = new ArrayList<ImpressionEntity>();
		doNetRequest();
	}

	/**
	 *
	 * 功能描述: 分页请求服务端数据
	 * @param
	 * @return void    返回类型
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-4-10 下午8:26:53
	 */
	private void doNetRequest() {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		String url = Constants.ServerParams.GET_ALL_IMPRESSIONS_URL;
		RequestParams params = new RequestParams();
		httpClient.addHeader("Content-Type", "multipart/form-data");
		httpClient.post(url, params, new AsyncHttpResponseHandler(){

			@Override
			public void onStart() {
				super.onStart();
				mDialog.show();
			}

			@Override
			@Deprecated
			public void onSuccess(int statusCode, String content) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, content);
				if (mDialog.isShowing()) {
					mDialog.dismiss();
				}
				if (isRefreshed) {
					//刷新完成后记得调用这个
					mWaterfallListView.onRefreshComplete();
					mImpressionList.clear();
				}
				try {
					JSONArray array = new JSONArray(content);
					ImpressionList list = JSONUtil.getMapper().readValue(JSONUtil.initJsonArrayStr(array.toString()), ImpressionList.class);
					if (mImpressionList != null && !list.getData().isEmpty()) {
						mImpressionList.addAll(list.getData());
						mHandler.sendEmptyMessage(GET_ALL_IMPRESSION_SUCCESS);
					}
				} catch (JSONException e) {
					mLog.e(e);
				}catch (JsonParseException e) {
					mLog.e(e);
				} catch (JsonMappingException e) {
					mLog.e(e);
				} catch (IOException e) {
					mLog.e(e);
				} catch (Exception e) {
					mLog.e(e);
				}
			}

			@Override
			@Deprecated
			public void onFailure(int statusCode, Throwable error,
								  String content) {
				super.onFailure(statusCode, error, content);
			}
		});
	}

	@Override
	protected void initEvent() {
		//设置下拉刷新监听
		mWaterfallListView.setOnRefreshListener(new MultiColumnPullToRefreshListView.OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				//下拉刷新要做的事
				doNetRequest();
				isRefreshed = true;
			}
		});

		//设置item点击监听
		mWaterfallListView.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
									int position, long id) {
				mLog.d("clicked position:"+position);
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
