package cn.cyansoft.contest.Yingxiang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import cn.cyansoft.contest.QQ.ImpressionEntity;
import cn.cyansoft.contest.R;

/**
 * 
 * @ClassName: WaterfallAdapter 
 * @Description: 瀑布流适配器
 * @author 欧阳海冰
 * @mail ouyang1738@gmail.com 
 * @date 2014年5月30日 下午10:43:26 
 *
 */
public class WaterfallAdapter extends BaseAdapter {

	private List<ImpressionEntity> mlist;
	private Context mContext;
	private Drawable drawable;
	private DisplayImageOptions options;

	public WaterfallAdapter(List<ImpressionEntity> mImpressionList,
							Context context) {
		this.mlist = mImpressionList;
		this.mContext = context;
		this.drawable = context.getResources().getDrawable(
				R.drawable.bucea_impression_icon_default);
		this.options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true)
						// 图片存本地
				.cacheInMemory(true).displayer(new FadeInBitmapDisplayer(50))
				.bitmapConfig(Bitmap.Config.RGB_565)
				.delayBeforeLoading(100)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
				.build();
	}


	@Override
	public int getCount() {
		return mlist != null ? mlist.size() : 0;
	}

	@Override
	public Object getItem(int arg0) {
		return mlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View view, ViewGroup group) {
		final Holder holder;
		// 得到View
		if (view == null) {
			holder = new Holder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			view = inflater.inflate(R.layout.bucea_waterfall_image_item, null);
			holder.ivIcon = (ImageView) view.findViewById(R.id.row_icon);
			holder.pbLoad = (ProgressBar) view.findViewById(R.id.pb_load);

			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}

		final ImpressionEntity impressionEntity = mlist.get(position);

		MyImageLoader.getInstance(mContext).displayImage(impressionEntity.getPicurl(), holder.ivIcon, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				holder.ivIcon.setImageDrawable(drawable);
				holder.pbLoad.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "Input/Output error";
						break;
					case DECODING_ERROR:
						message = "can not be decoding";
						break;
					case NETWORK_DENIED:
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:
						message = "内存不足";
						Toast.makeText(mContext, message,
								Toast.LENGTH_SHORT).show();
						break;
					case UNKNOWN:
						message = "Unknown error";
						Toast.makeText(mContext, message,
								Toast.LENGTH_SHORT).show();
						break;
				}
				holder.pbLoad.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				holder.pbLoad.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {

			}
		});


		holder.ivIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i("TAG", "image click" + position);
				Intent intent = new Intent(mContext, ImpressionDetailActivity.class);
				Bundle extras = new Bundle();
				extras.putSerializable("impression_entity", impressionEntity);
				intent.putExtras(extras);
				mContext.startActivity(intent);
			}
		});

		return view;
	}
}
	class Holder {
		public ImageView ivIcon;
		public ProgressBar pbLoad;
}

