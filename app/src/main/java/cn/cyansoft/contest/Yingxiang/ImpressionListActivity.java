package cn.cyansoft.contest.Yingxiang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.QQ.ImpressionEntity;
import cn.cyansoft.contest.QQ.ImpressionListAdapter;
import cn.cyansoft.contest.R;


public class ImpressionListActivity extends BaseActivity implements OnClickListener {
	
	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private ListView mListView;
	private List<ImpressionEntity> mImpressionList;
	private ImpressionListAdapter mListAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.qq_bucea_impression_list);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.impression_list_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		mExtension.setClickable(false);
		mIvExtension.setVisibility(View.INVISIBLE);
		mListView = (ListView) findViewById(R.id.lv_impression_list);
	}

	@Override
	protected void initData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String type = bundle.getString("type");
		mImpressionList = (List<ImpressionEntity>) intent.getSerializableExtra("impression_list");
		if (type!=null && type.equals("my_impression")) {
			mTvTitle.setText("我的印象");
		}else if (type!=null && type.equals("my_likes")) {
			mTvTitle.setText("我的喜欢");
		}
		if (mImpressionList!=null && !mImpressionList.isEmpty()) {
			if (mListAdapter == null) {
				mListAdapter = new ImpressionListAdapter(ImpressionListActivity.this, mImpressionList);
				mListView.setAdapter(mListAdapter);
			}else {
				mListAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(this);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent(ImpressionListActivity.this, ImpressionDetailActivity.class);
				Bundle extras = new Bundle();
				extras.putSerializable("impression_entity", mImpressionList.get(position));
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == mBack) {
			this.finish();
		}
	}

}
