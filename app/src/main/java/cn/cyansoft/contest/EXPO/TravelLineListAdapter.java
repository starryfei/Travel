package cn.cyansoft.contest.EXPO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.cyansoft.contest.EXPO.TravelLineItem;
import cn.cyansoft.contest.R;

/**
 * 
 * @ClassName: TravelLineListAdapter 
 * @Description: ListView列表适配器
 * @author 欧阳海冰（OuyangHaibing） 
 * @mail ouyang1738@gmail.com
 * @date 2014年6月9日 下午5:08:15 
 *
 */
public class TravelLineListAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<TravelLineItem> mItemList;
	private LayoutInflater mInflater;

	public TravelLineListAdapter(List<TravelLineItem> items, Context context) {
		this.mContext = context;
		this.mItemList = items;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (holder == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.bucea_travel_line_list_item, null);
			holder.iv_travel_line_shortcut = (ImageView) convertView.findViewById(R.id.iv_travel_line_shortcut);
			holder.tv_travel_line_title = (TextView) convertView.findViewById(R.id.tv_travel_line_title);
			holder.tv_travel_line_desc = (TextView) convertView.findViewById(R.id.tv_travel_line_desc);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		TravelLineItem lineItem = mItemList.get(position);
		holder.iv_travel_line_shortcut.setBackgroundResource(lineItem.getPic_id());
		holder.tv_travel_line_title.setText(lineItem.getTitle());
		holder.tv_travel_line_desc.setText(lineItem.getDesc());
		
		return convertView;
	}
	
	public static class ViewHolder{
		private ImageView iv_travel_line_shortcut;
		private TextView tv_travel_line_title;
		private TextView tv_travel_line_desc;
	}

}
