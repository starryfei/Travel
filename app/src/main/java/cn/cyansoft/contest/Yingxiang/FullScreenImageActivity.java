package cn.cyansoft.contest.Yingxiang;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 
 * @ClassName: FullScreenImageActivity 
 * @Description: 图片点击后全屏显示
 * @author 欧阳海冰
 * @mail ouyang1738@gmail.com 
 * @date 2014年5月30日 下午10:40:54 
 *
 */
public class FullScreenImageActivity extends Activity {

	private String image_url;
	private int mLocationX;
	private int mLocationY;
	private int mWidth;
	private int mHeight;
	private SmoothImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		image_url = (String) getIntent().getSerializableExtra("image_url");
		mLocationX = getIntent().getIntExtra("locationX", 0);  
		mLocationY = getIntent().getIntExtra("locationY", 0);  
		mWidth = getIntent().getIntExtra("width", 0);  
		mHeight = getIntent().getIntExtra("height", 0); 
		
		imageView = new SmoothImageView(this);  
		imageView.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);  
		imageView.transformIn();  
		imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
		imageView.setScaleType(ScaleType.FIT_CENTER);
		setContentView(imageView);  
		ImageLoader.getInstance().displayImage(image_url, imageView);
	}
	
	@Override
	public void onBackPressed() {
		imageView.setOnTransformListener(new SmoothImageView.TransformListener() {
			@Override
			public void onTransformComplete(int mode) {
				if (mode == 2) {
					finish();
				}
			}
		});
		imageView.transformOut();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}
	
}
