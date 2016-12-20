package cn.cyansoft.contest.QQ;

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

/**
 * 
 * @ClassName: ImpressionListAdapter 
 * @Description: 印象列表适配器 
 * @author 欧阳海冰
 * @mail ouyang1738@gmail.com 
 * @date 2014年5月28日 下午3:49:50 
 *
 */
public class ImpressionListAdapter extends BaseAdapter {

	private Context mContext;
	private List<ImpressionEntity> mImpressionList;
	private LayoutInflater mInflater;
	private DisplayImageOptions options;

	public ImpressionListAdapter(Context context, List<ImpressionEntity> impressionList) {
		this.mContext = context;
		this.mImpressionList = impressionList;
		this.mInflater = LayoutInflater.from(mContext);
		this.options=new DisplayImageOptions.Builder().showStubImage(R.drawable.bucea_poi_info_default)
				.showImageForEmptyUri(R.drawable.bucea_poi_info_default)
				.showImageOnFail(R.drawable.bucea_poi_info_default)
				.displayer(new RoundedBitmapDisplayer(7))
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
	}
	
	@Override
	public int getCount() {
		return mImpressionList.size();
	}

	@Override
	public Object getItem(int position) {
		return mImpressionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.qq_bucea_impression_list_item, null);
			holder.iv_impression_shortcut = (ImageView) convertView.findViewById(R.id.iv_impression_shortcut);
			holder.tv_impression_by_nickname = (TextView) convertView.findViewById(R.id.tv_impression_by_nickname);
			holder.tv_impression_time = (TextView) convertView.findViewById(R.id.tv_impression_time);
			holder.tv_impression_summary = (TextView) convertView.findViewById(R.id.tv_impression_summary);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ImpressionEntity impressionEntity = mImpressionList.get(position);
		//设置头像
		MyImageLoader.getInstance(mContext).displayImage(impressionEntity.getPicurl(), holder.iv_impression_shortcut,options);
		//设置昵称
		holder.tv_impression_by_nickname.setText(impressionEntity.getUsername());
		//设置时间
		holder.tv_impression_time.setText(impressionEntity.getUploadDate());
		//设置评论
		holder.tv_impression_summary.setText(impressionEntity.getContent());
		
		return convertView;
	}
	
	public static class ViewHolder{
		private ImageView iv_impression_shortcut;
		private TextView tv_impression_by_nickname;
		private TextView tv_impression_time;
		private TextView tv_impression_summary;
	}

}
