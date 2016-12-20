package cn.cyansoft.contest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.cyansoft.contest.EXPO.ExpoActivity;
import cn.cyansoft.contest.School.SchoolActivity;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.GrouponActivity;
import cn.cyansoft.contest.ditu.DituActivity;

/**
 * win8风格的界面
 * @author ouyang1738
 *
 */
public class GuideFragment extends BaseFragment implements OnClickListener {

	private Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.guide_fragment, null);
		initView(view);
		initData();
		initEvent();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initView(View view) {
		view.findViewById(R.id.nature_science).setOnClickListener(this);
		view.findViewById(R.id.city_life).setOnClickListener(this);
		view.findViewById(R.id.expo_route).setOnClickListener(this);
		view.findViewById(R.id.group_information).setOnClickListener(this);
		view.findViewById(R.id.direction_recognize).setOnClickListener(this);
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initEvent() {
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		//笔架山
		if (id == R.id.nature_science) {
			Intent intent = new Intent(mContext,DituActivity.class);
			startActivity(intent);
		}
		//世博园攻略
		else if (id == R.id.city_life ) {
			Intent intent = new Intent(mContext, ExpoActivity.class);
			startActivity(intent);
		}
		//辽沈战役纪念馆
		else if (id == R.id.expo_route ) {
			Intent intent = new Intent(mContext, LiaoshenActivity.class);
			startActivity(intent);
		}
		//团购信息
		else if(id == R.id.group_information){
			Intent intent = new Intent(mContext, GrouponActivity.class);
			startActivity(intent);
		}//周边学校
		else if(id == R.id.direction_recognize){
			Intent intent = new Intent(mContext, SchoolActivity.class);
			startActivity(intent);
		}
	}

}
