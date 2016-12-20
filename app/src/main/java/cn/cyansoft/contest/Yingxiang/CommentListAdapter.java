package cn.cyansoft.contest.Yingxiang;

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

import cn.cyansoft.contest.QQ.CommentEntity;
import cn.cyansoft.contest.R;

/**
 * 
 * @ClassName: CommentListAdapter 
 * @Description: 评论列表适配器 
 * @author 欧阳海冰
 * @mail ouyang1738@gmail.com 
 * @date 2014年5月28日 下午3:49:50 
 *
 */
public class CommentListAdapter extends BaseAdapter {

	private Context mContext;
	private List<CommentEntity> mCommentList;
	private LayoutInflater mInflater;
	private DisplayImageOptions options;

	public CommentListAdapter(Context context, List<CommentEntity> commentList) {
		this.mContext = context;
		this.mCommentList = commentList;
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
		return mCommentList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCommentList.get(position);
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
			convertView = mInflater.inflate(R.layout.qq_bucea_comment_list_item, null);
			holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_header);
			holder.tv_comment_nickname = (TextView) convertView.findViewById(R.id.tv_comment_nickname);
			holder.tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
			holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		CommentEntity commentEntity = mCommentList.get(position);
		//设置头像
		MyImageLoader.getInstance(mContext).displayImage(commentEntity.getHeader_url(), holder.iv_header,options);
		//设置昵称
		holder.tv_comment_nickname.setText(commentEntity.getName());
		//设置时间
		holder.tv_comment_time.setText(commentEntity.getDate());
		//设置评论
		holder.tv_comment.setText(commentEntity.getComment());
		
		return convertView;
	}
	
	public static class ViewHolder{
		private ImageView iv_header;
		private TextView tv_comment_nickname;
		private TextView tv_comment_time;
		private TextView tv_comment;
	}

}
