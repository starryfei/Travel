package cn.cyansoft.contest.EXPO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.R;

public class ScenerySpotsActivity extends BaseActivity implements OnClickListener{

	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private GridView mGridView;
	private ScenerySpotsAdapter spotsAdapter;
	private String mCategory;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bucea_scenery_spot);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.scenery_spots_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mExtension.setVisibility(View.INVISIBLE);
		mExtension.setClickable(false);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		mGridView = (GridView) findViewById(R.id.gv_scenery_spots);
	}

	@Override
	protected void initData() {
		Intent intent = getIntent();
		mCategory = intent.getStringExtra("category");
		if (mCategory.equals("celebrity")) {//初始化名人列表
			mTvTitle.setText(getString(R.string.expo_nature_title));
			mGridView.setNumColumns(3);
		}else if(mCategory.equals("scenery_spots")){//初始化特色景点
			mTvTitle.setText(getString(R.string.expo_city_title));
			mGridView.setNumColumns(2);
		}
		if (spotsAdapter == null) {
			spotsAdapter =  new ScenerySpotsAdapter(ScenerySpotsActivity.this,mCategory);
			mGridView.setAdapter(spotsAdapter);
		}else {
			spotsAdapter.notifyDataSetChanged();
		}

	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(this);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
									long arg3) {
				Intent intent = new Intent(ScenerySpotsActivity.this,ScenerySpotsDetailActivity.class);
				intent.putExtra("category", mCategory);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View view) {
		if (view == mBack) {
			this.finish();
		}
	}

}
