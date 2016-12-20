package cn.cyansoft.contest.QQ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.cyansoft.contest.R;


public class UserCenterAdapter extends BaseAdapter {

	
	List<UserItem> mList;
	private Context mContext;
	private LayoutInflater mInflater;
	
	public UserCenterAdapter(Context context, List<UserItem> list) {
		this.mContext = context;
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.qq_bucea_user_center_listitem, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		UserItem item = mList.get(position);
		holder.tvName.setText(item.getItemName());
		return convertView;
	}
	
	static class ViewHolder{
		TextView tvName;
	}

}
