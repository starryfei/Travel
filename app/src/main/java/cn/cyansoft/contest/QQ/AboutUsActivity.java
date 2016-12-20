package cn.cyansoft.contest.QQ;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.R;

public class AboutUsActivity extends BaseActivity {

	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private GridView mGv;
	private AboutUsGridAdapter mUsGridAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.qq_bucea_about_us);
		initView();
		initData();
		initEvent();
	}
	
	@Override
	protected void initView() {

		mTitleBar = findViewById(R.id.about_us_title_bar);
		mBack = mTitleBar.findViewById(R.id.head_back);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mTvTitle.setText("团队介绍");
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mExtension.setClickable(false);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		mIvExtension.setVisibility(View.GONE);
		
		mGv = (GridView) findViewById(R.id.gv_about_us);
		
	}

	@Override
	protected void initData() {
		if (mUsGridAdapter == null) {
			mUsGridAdapter = new AboutUsGridAdapter(AboutUsActivity.this);
			mGv.setAdapter(mUsGridAdapter);
		}else {
			mUsGridAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AboutUsActivity.this.finish();
			}
		});
	}

}
