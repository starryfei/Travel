package cn.cyansoft.contest.EXPO;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;


import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.R;

public class TrafficGuideActivity extends BaseActivity implements OnClickListener{

	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bucea_traffic_guide);
		initView();
		initData();
		initEvent();
	}
	
	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.traffic_guide_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
	}

	@Override
	protected void initData() {
		mTvTitle.setText(getString(R.string.expo_travel_title));
		mExtension.setClickable(false);
		mIvExtension.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mBack) {
			this.finish();
		}
	}

}
