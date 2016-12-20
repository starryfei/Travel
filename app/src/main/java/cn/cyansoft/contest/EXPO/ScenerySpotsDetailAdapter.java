package cn.cyansoft.contest.EXPO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.cyansoft.contest.R;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.MyImageLoader;


public class ScenerySpotsDetailAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	List<ParagraphItem> mList;

	public ScenerySpotsDetailAdapter(Context context, List<ParagraphItem> list) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mList = list;
	}


	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.bucea_scenery_spots_detail_list_item, null);
			holder.tv_paragraph = (TextView) convertView.findViewById(R.id.tv_paragraph);
			holder.iv_paragraph = (ImageView) convertView.findViewById(R.id.iv_paragraph);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		ParagraphItem item = mList.get(position);
		holder.tv_paragraph.setText(mContext.getString(item.getParagraphId()));
		MyImageLoader.getInstance(mContext).displayImage(item.getParagraphUrl(),holder.iv_paragraph);

		return convertView;
	}

	static class ViewHolder{
		TextView tv_paragraph;
		ImageView iv_paragraph;
	}

}
