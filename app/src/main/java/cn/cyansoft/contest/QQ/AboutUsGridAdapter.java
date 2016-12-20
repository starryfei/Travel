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

import cn.cyansoft.contest.R;


/**
 * 
 * @ClassName: AboutUsGridAdapter 
 * @Description: GridView列表适配器
 * @author 欧阳海冰（OuyangHaibing） 
 * @mail ouyang1738@gmail.com
 * @date 2014年6月9日 下午5:15:08 
 *
 */
public class AboutUsGridAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private String[] txtArr;
	private int[] imgArrs;
	private DisplayImageOptions options;
	
	public AboutUsGridAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		init();
	}
	private void init() {
		txtArr = new String[]{ "程序猿：小欧","设计狮：若恒","数据采集：佳臣","测试：刘洋"};
		imgArrs = new int[]{
				R.drawable.bucea_about_us_xiaoou,R.drawable.bucea_about_us_ruoheng,
				R.drawable.bucea_about_us_jiacheng,R.drawable.bucea_about_us_liuyang,};
		this.options=new DisplayImageOptions.Builder()
				.displayer(new RoundedBitmapDisplayer(15))
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
	}
	@Override
	public int getCount() {
		return txtArr.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (holder == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.qq_bucea_about_us_gridview_item, null);
			holder.iv_scenery_spots_short_cut = (ImageView) convertView.findViewById(R.id.iv_scenery_spots_short_cut);
			holder.tv_scenery_spots_name = (TextView) convertView.findViewById(R.id.tv_scenery_spots_name);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_scenery_spots_name.setText(txtArr[position]);
		MyImageLoader.getInstance(context).displayImage("drawable://"+imgArrs[position], holder.iv_scenery_spots_short_cut,options);
		return convertView;
	}
	
	static class ViewHolder{
		private ImageView iv_scenery_spots_short_cut;
		private TextView tv_scenery_spots_name;
	}

}

