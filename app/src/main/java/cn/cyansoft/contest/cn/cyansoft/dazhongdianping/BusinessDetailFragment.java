package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.cyansoft.contest.BaseFragment;
import cn.cyansoft.contest.R;

public class BusinessDetailFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private WebView mWebView;
    private ProgressDialog mDialog;
    private LinearLayout Back;
    private LinearLayout HeadRight;
    private TextView HeadTitle;
    private FragmentManager mFragmentManager;
    private ImageView Share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.groupon_business_detail_fragment, null);
        initView(view);
        initEvent();
        initData();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFragmentManager = getFragmentManager();
    }

    private void initView(View view) {
        mDialog = new ProgressDialog(mActivity);
        mWebView = (WebView) view.findViewById(R.id.business_page_wv);
        Back = (LinearLayout) view.findViewById(R.id.head_back);
        HeadTitle = (TextView) view.findViewById(R.id.head_title);
        HeadRight = (LinearLayout) view.findViewById(R.id.head_share);
        Share = (ImageView) view.findViewById(R.id.share);
        HeadRight.setClickable(false);
        Share.setVisibility(View.INVISIBLE);
        //开启JS支持
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mDialog.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    protected void initData() {
        mDialog.setMessage("正在加载数据，请稍候...");
        HeadTitle.setText("商户详情");
        String businessUrl = getArguments().getString("businessUrl");
        this.mWebView.clearCache(true);
        this.mWebView.clearHistory();
        this.mWebView.loadUrl(businessUrl);
    }

    public void webviewGoBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    @Override
    protected void initEvent() {
        Back.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mWebView.destroy();
    }

    @Override
    public void onClick(View v) {
        //回退按钮事件
        if (v == Back) {
            if(this.mFragmentManager.getBackStackEntryCount()==0){
                mActivity.finish();
            }else{
                this.mFragmentManager.popBackStack();
            }
        }
    }
}