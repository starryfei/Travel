package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cyansoft.contest.BaseFragment;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;

public class GrouponListFragment extends BaseFragment implements View.OnClickListener {

    public final static int LISTVIEW_DATA_MORE = 0x01;
    public final static int LISTVIEW_DATA_LOADING = 0x02;
    public final static int LISTVIEW_DATA_FULL = 0x03;
    public final static int LISTVIEW_DATA_EMPTY = 0x04;

    private static final int INIT = 0;
    private static final int LOADINGMORE = 1;
    private static final int REFRESH = 2;

    private static final int POPUP_ITEM_HEIGHT =120;

    private Activity mActivity;
    private LinearLayout Back;
    private TextView HeadTitle;
    private PullToRefreshListView mFoodLv;
    private List<GrouponEntity> mData;
    private View mLvFooter;
    private TextView lvFood_foot_more;
    private ProgressBar lvFood_foot_progress;
    private ProgressDialog mDialog;
    private LinearLayout mDzdpInfoLl;
    private int pageNum=1;
    private GrouponListAdapter mlistAdapter;
    private int requestCount;
    private String mCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groupon_list_fragment, null);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private Handler mHandler = new Handler(){

        public void dispatchMessage(android.os.Message msg) {
            String json = (String) msg.obj;
            int loadType = msg.what;
            if (loadType == INIT) {
                mDialog.dismiss();
            }
            Log.d("json------>>pageNum="+pageNum, json);
            try {
                JSONObject obj = new JSONObject(json);
                String status = obj.getString("status");
                if (status.equals("OK")) {
                    mDzdpInfoLl.setVisibility(View.VISIBLE);
                    //本次API访问所获取的商户数量
                    int count = obj.getInt("count");
                    //保存所有请求的数量
                    requestCount += count;
                    //所有页面商户总数，最多为40条
                    int total_count = obj.getInt("total_count");
                    //表明还未请求完
                    if (requestCount < total_count) {
                        //如果两者相等，表示没有更多了
                        mFoodLv.setTag(LISTVIEW_DATA_MORE);
                        lvFood_foot_more.setText("更多");
                    }else {//已请求所有数据
                        mFoodLv.setTag(LISTVIEW_DATA_FULL);
                        lvFood_foot_more.setText("已加载全部");
                        lvFood_foot_progress.setVisibility(View.GONE);
                    }

                    String arrayStr = obj.getString("deals");
                    JSONArray array = new JSONArray(arrayStr);
                    try {
                        GrouponList grouponList = JSONUtil.getMapper().
                                readValue(JSONUtil.initJsonArrayStr(array.toString()), GrouponList.class);
                        if (mData!= null && !grouponList.getData().isEmpty()) {
                            if (pageNum == 1) {
                                if (loadType == REFRESH) {
                                    mFoodLv.onRefreshComplete();
                                    if(mData.get(0).getDeal_id()==grouponList.getData().get(0).getDeal_id()){
                                        //表明刷新出的数据与之前是完全相同的
                                        Toast.makeText(mActivity, "没有新更新", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                mData.clear();
                            }
                            mData.addAll(grouponList.getData());
                            mlistAdapter.notifyDataSetChanged();
                        }
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    private LinearLayout HeadRight;
    private ImageView Share;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initView(View view) {
        mDialog = new ProgressDialog(mActivity);
        mDialog.setMessage("正在加载数据，请稍候...");
        mDialog.setCancelable(false);
        Back = (LinearLayout) view.findViewById(R.id.head_back);
        HeadTitle = (TextView) view.findViewById(R.id.head_title);
        mDzdpInfoLl = (LinearLayout) view.findViewById(R.id.dzdp_info);
        HeadRight = (LinearLayout) view.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) view.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);
        mFoodLv = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_lv);
        mLvFooter = getActivity().getLayoutInflater().inflate(R.layout.groupon_listview_footer,
                null);
        lvFood_foot_more = (TextView) mLvFooter
                .findViewById(R.id.listview_foot_more);
        lvFood_foot_progress = (ProgressBar) mLvFooter
                .findViewById(R.id.listview_foot_progress);
        mFoodLv.addFooterView(mLvFooter);// 添加底部视图 必须在setAdapter前

    }

    @Override
    protected void initData() {
        requestCount = 0;
        mData = new ArrayList<GrouponEntity>();
        mlistAdapter = new GrouponListAdapter(mActivity, mData);
        mFoodLv.setAdapter(mlistAdapter);
        HeadTitle.setText("周边团购");
        loadFoodListData(pageNum,mCategory,INIT);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    /**
     * 请求大众点评API数据
     * @param pageNum
     * @param loadType
     */
    private void loadFoodListData(final int pageNum,final String category,final int loadType) {
        //判断是否有网
        if(SystemUtil.isNetworkConnected(mActivity)){
            if (INIT == loadType) {
                mDialog.show();
            }
            new Thread(){
                public void run() {
                    Map<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("city", "锦州");
                    paramMap.put("latitude", Constants.LATITUDE);
                    paramMap.put("longitude", Constants.LONGITUDE);
                      if((category !=null)&&!StringUtils.isEmpty(category)){
                        paramMap.put("category", category);
                    }
                    paramMap.put("region", "古塔区");
                    paramMap.put("limit", "20");
                    paramMap.put("page", String.valueOf(pageNum));
                    paramMap.put("radius", "2000");
                    paramMap.put("format", "json");

                    String requestResult = DianpingApiTool.requestApi(Constants.DZDianpingParams.GROUPON_API_URL, Constants.DZDianpingParams.DP_APP_KEY, Constants.DZDianpingParams.DP_APP_SECRET, paramMap);
                    Message msg = Message.obtain();
                    msg.what = loadType;
                    msg.obj = requestResult;
                    mHandler.sendMessage(msg);
                };
            }.start();

        }else {

        }
    }
    @Override
    protected void initEvent() {
        Back.setOnClickListener(this);
        /**
         * 自动加载更多
         */
        mFoodLv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mFoodLv.onScrollStateChanged(view, scrollState);
                // 数据为空--不用继续下面代码了
                if (mData.isEmpty())
                    return;

                // 判断是否滚动到底部
                boolean scrollEnd = false;
                try {
                    if (view.getPositionForView(mLvFooter) == view.getLastVisiblePosition()){
                        scrollEnd = true;
                    }
                } catch (Exception e) {
                    scrollEnd = false;
                }

                int lvDataState =StringUtils.toInt(mFoodLv.getTag());
                if (scrollEnd && lvDataState==LISTVIEW_DATA_MORE) {
                    lvFood_foot_more.setText("加载中···");
                    lvFood_foot_progress.setVisibility(View.VISIBLE);
                    pageNum++;
                    loadFoodListData(pageNum,mCategory,LOADINGMORE);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
                mFoodLv.onScroll(view, firstVisiableItem, visibleItemCount, totalItemCount);
            }
        });

        /**
         * 下拉刷新
         */
        mFoodLv.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                pageNum = 1;
                requestCount = 0;
                loadFoodListData(pageNum,mCategory,REFRESH);
            }
        });
        mFoodLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                Log.d("------------>>position:", position+"");
                //点击是footerView
                if (position==mData.size()+1) {
                    return;
                }
                String businessUrl = mData.get(position-1).getDeal_h5_url();
                Bundle bundle = new Bundle();
                bundle.putString("businessUrl", businessUrl);
                bundle.putString("telPhone", "1554226327");
                ((GrouponActivity)mActivity).changeFragment(bundle);
            }
        });

        mFoodLv.setClickable(false);

    }
    @Override
    public void onClick(View view) {
        if (view == Back) {
            mActivity.finish();
        }else if (view == HeadRight) {
            int y = HeadRight.getBottom() * 3 / 2;
            int x = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 4;
            showPopupWindow(x, y);
        }
    }

    /**
     * 功能描述: 显示筛选PopupWindow
     * @author 欧阳海冰(OuyangHaibing)
     * @param @param x
     * @param @param y
     * @return void
     * @throws
     */
    private void showPopupWindow(int x, int y) {
        LinearLayout popupLayout = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.groupon_query_popup_dialog, null);
        ListView listView = (ListView) popupLayout.findViewById(R.id.mgp_feedback_query_lv_dialog);
        final String[] textArr = getResources().getStringArray(R.array.groupon_category);
        listView.setAdapter(new ArrayAdapter<String>(mActivity,
                R.layout.groupon_query_popup_item_text, R.id.tv_text, textArr));

        final PopupWindow popupWindow = new PopupWindow(mActivity);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setWidth(mActivity.getWindowManager().getDefaultDisplay().getWidth() * 7/16);
        popupWindow.setHeight(POPUP_ITEM_HEIGHT * textArr.length);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(popupLayout);
        // showAsDropDown会把里面的view作为参照物，所以要那满屏幕parent
        popupWindow.showAsDropDown(mActivity.findViewById(R.id.head_share), x, 10);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (position == 0) {
                    mCategory = "";
                }else {
                    mCategory = textArr[position];
                }
                pageNum = 1;
                requestCount = 0;
                loadFoodListData(pageNum,mCategory,INIT);
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
