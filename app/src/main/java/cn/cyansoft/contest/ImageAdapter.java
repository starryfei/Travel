package cn.cyansoft.contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/9 0009.
 */
 public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int position;
    private View convertView;
    private ViewGroup parent;

    private Context mContext;

    public String[] img_text = { "旅游实景", "最新旅游资讯", "锦州世博园", "辽沈战役纪念馆", "笔架山", "北普陀山", "辽宁工业大学", "地图",
            "大众点评",   };
    public int[] imgs = { R.drawable.wenhua1, R.drawable.tesemeishi1, R.drawable.expo1,
            R.drawable.liaoshen1, R.drawable.bijiashan1,
            R.drawable.beiputuo1, R.drawable.liaogong1,
            R.drawable.map1, R.drawable.dazhong1 };

    public ImageAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_mygridiew, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }
}
