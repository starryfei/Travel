package cn.cyansoft.contest.EXPO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.cyansoft.contest.R;


/**
 *
 * @ClassName: ScenerySpotsAdapter
 * @Description: GridView列表适配器
 * @author 欧阳海冰（OuyangHaibing） 
 * @mail ouyang1738@gmail.com
 * @date 2014年6月9日 下午5:15:08 
 *
 */
public class ScenerySpotsAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private String[] txtArr;
	private int[] imgArrs;
	private String type;

	public ScenerySpotsAdapter(Context context, String type) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.type = type;
		init(type);
	}
	private void init(String type) {
		if (type.equals("celebrity")) {
			txtArr = new String[]{
					"国际生态馆","水韵之舞剧场","百花馆"
					,"百花塔","海洋科技创意馆","迷宫游苑"
					,"演绎广场","海湾花田","牡丹芍药园"
					,"百草园","杏花园","张和国际树化石园"};
			imgArrs = new int[]{
					R.drawable.guojisheng, R.drawable.shuiyu,
					R.drawable.baihuaguan,R.drawable.baihuata,
					R.drawable.haiyang,R.drawable.migong,
					R.drawable.yanyiguangchang,R.drawable.haiwan,
					R.drawable.mudan,R.drawable.baicaoyuan,
					R.drawable.xinghuayuan,R.drawable.shihua};
		}else if (type.equals("scenery_spots")) {
			txtArr = new String[]{
					"荷兰","葡萄牙"
					,"哥伦比亚","西班牙"
					,"新西兰","伊朗"
					,"墨西哥","鞍山"
					,"锦州","本溪"
					,"阜新","铁岭"
					,"葫芦岛","盘锦"
					,"营口","丹东"
					,"大连","沈阳"
					,"抚顺","朝阳"
			};
			imgArrs = new int[]{
					R.drawable.helan,R.drawable.putaoya,
					R.drawable.gelubiya,R.drawable.xibanya,
					R.drawable.xinxilan,R.drawable.yilang,
					R.drawable.moxige,R.drawable.anshang,
					R.drawable.jinzhou,R.drawable.benxi,
					R.drawable.fuxin,R.drawable.tieling,
					R.drawable.huludao,R.drawable.panjin,
					R.drawable.yingkou,R.drawable.dandong,
					R.drawable.dalian,R.drawable.shenyang,
					R.drawable.fushunyuan,R.drawable.chaoyang,
			};
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (holder == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bucea_scenery_spots_gridview_item, null);
			holder.iv_scenery_spots_short_cut = (ImageView) convertView.findViewById(R.id.iv_scenery_spots_short_cut);
			holder.tv_scenery_spots_name = (TextView) convertView.findViewById(R.id.tv_scenery_spots_name);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_scenery_spots_name.setText(txtArr[position]);
		holder.iv_scenery_spots_short_cut.setImageResource(imgArrs[position]);

		return convertView;
	}

	static class ViewHolder{
		private ImageView iv_scenery_spots_short_cut;
		private TextView tv_scenery_spots_name;
	}

}