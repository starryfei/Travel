package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

import cn.cyansoft.contest.R;


public class GrouponListAdapter extends BaseAdapter {

	private List<GrouponEntity> mDataList;
	private Context mContext;
	private LayoutInflater mInflater;
	private DisplayImageOptions options;
	
	public GrouponListAdapter(Context context, List<GrouponEntity> list) {
		this.mContext = context;
		this.mDataList = list;
		this.mInflater = LayoutInflater.from(mContext);
		this.options=new DisplayImageOptions.Builder().showStubImage(R.drawable.groupon_poi_info_default)
				.showImageForEmptyUri(R.drawable.groupon_poi_info_default)
				.showImageOnFail(R.drawable.groupon_poi_info_default)
				.displayer(new RoundedBitmapDisplayer(10))
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
	}
	
	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.groupon_list_item, null);
			holder = new ViewHolder();
			holder.foodPhotoIv = (ImageView) convertView.findViewById(R.id.iv_photo);
			holder.grouponName = (TextView) convertView.findViewById(R.id.tv_groupon_name);
			holder.currentPriceTv = (TextView) convertView.findViewById(R.id.tv_current_price);
			holder.listPriceTv = (TextView) convertView.findViewById(R.id.tv_list_price);
			holder.distanceTv = (TextView) convertView.findViewById(R.id.tv_distance);
			holder.purchaseCountTv = (TextView) convertView.findViewById(R.id.tv_purchase_count);
			holder.regionsTv = (TextView) convertView.findViewById(R.id.tv_regions);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		GrouponEntity entity = mDataList.get(position);
		//设置图片
		MyImageLoader.getInstance(mContext).displayImage(entity.getS_image_url(), holder.foodPhotoIv,options);
		
		holder.grouponName.setText(entity.getTitle());
		holder.currentPriceTv.setText("￥"+entity.getCurrent_price());
		holder.listPriceTv.setText("￥"+entity.getList_price());
		holder.distanceTv.setText(entity.getDistance()+"m");
		holder.purchaseCountTv.setText(entity.getPurchase_count()+"人");
		
		List<String> regionsList = entity.getRegions();
		if (regionsList.size() == 1) {
			holder.regionsTv.setText(regionsList.get(0));
		}else if (regionsList.size() == 2) {
			holder.regionsTv.setText(regionsList.get(0)+"等");
		}else if (regionsList.size() > 2){
			holder.regionsTv.setText(regionsList.get(0)+"/"+regionsList.get(1)+"等");
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView foodPhotoIv;
		TextView grouponName;
		TextView currentPriceTv;
		TextView listPriceTv;
		TextView distanceTv;
		TextView purchaseCountTv;
		TextView regionsTv;
	}

}
