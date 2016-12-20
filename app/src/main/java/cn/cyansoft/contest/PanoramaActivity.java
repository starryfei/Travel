package cn.cyansoft.contest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tencentmap.streetviewsdk.Marker;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaFinishListner;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaCamera;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaView;

public class PanoramaActivity extends Activity implements
OnStreetViewPanoramaChangeListener, OnStreetViewPanoramaFinishListner,
OnStreetViewPanoramaCameraChangeListener, OnClickListener {
	private PopupMenu popMenu;
	private StreetViewPanoramaView mPanoramaView;
	private StreetViewPanorama  mPanorama;
	private RelativeLayout mControlPanel;
	private Animation controlAppear;
	private Animation controlVanish;
	
	private boolean streetViewPanoramaState = false;
	private LinearLayout Back;    //返回键
	private LinearLayout HeadRight;
	private View Head;
	private TextView HeadTitle;  //标题
	private ImageView Share;   //更多

	private Double mLontitude = 41.149100;
	private Double lLontutide = 121.122411;
	private String id_jiejing ="14071122130717101117700";
	private String text ="辽宁工业大学";
    private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.panarama_activity);

		Head = findViewById(R.id.title_bar);
		HeadTitle = (TextView) Head.findViewById(R.id.head_title);
		HeadTitle.setText("旅游实景");
		Back = (LinearLayout) findViewById(R.id.head_back);
		Back.setVisibility(View.VISIBLE);
		Back.setOnClickListener(this);
		HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
		HeadRight.setClickable(false);
		Share = (ImageView) Head.findViewById(R.id.share);
//        Share.setVisibility(View.INVISIBLE);
		Share.setBackgroundResource(R.drawable.head_menu_btn);
		Share.setOnClickListener(this);
		this.context = this;

		init();
	}
	@Override
	public void onClick(View v) {
		if(v==Back){
			this.finish();
		}
		if (v == Share) {
			popMenu = new PopupMenu(context, Share);
			getMenuInflater().inflate(R.menu.jiejing, popMenu.getMenu());
			popMenu.show();
			popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					// TODO Auto-generated method stub
					switch (item.getItemId()) {
						case R.id.id_liaogong:
							mLontitude = 41.149100;
							 lLontutide = 121.122411;
							id_jiejing="14071122130717101117700";
							text = "辽宁工业大学";
							init();
							break;
						case R.id.id_expo:
							mLontitude = 40.906588;
							lLontutide = 121.207287;
							id_jiejing="140730X5130721150440800";
							text ="锦州世博园";
							init();
							break;
						case R.id.id_liaoshen:
							mLontitude = 41.131990;
							lLontutide = 121.147810 ;
							id_jiejing="140731X5130719131457200";
							text = "辽沈纪念馆";
							init();
							break;
						case R.id.id_bijiashan:
							mLontitude = 40.828350;
							lLontutide = 121.073950;
							id_jiejing="140731X5130720142327600";
							text ="笔架山";
							init();
							break;
						case R.id.id_beiputuo:
							mLontitude = 41.173230;
							lLontutide =121.049740;
							id_jiejing="14071022130706161247900";
							text = "北普陀山";
							init();
							break;
					}
					return false;
				}
			});

		}
	}
	
	private void init() {
		mPanoramaView = (StreetViewPanoramaView)findViewById(R.id.panorama_view);
		mPanorama = mPanoramaView.getStreetViewPanorama();
		//初始化地点
		mPanorama.setPosition(mLontitude,lLontutide);
//		mPanorama.setPosition("10011001120307113241300");
		mPanorama.setOnStreetViewPanoramaChangeListener(this);
		mPanorama.setOnStreetViewPanoramaFinishListener(this);
		mPanorama.setOnStreetViewPanoramaCameraChangeListener(this);
		mControlPanel = (RelativeLayout)findViewById(R.id.layout_orientation_panel);
		mControlPanel.setAnimationCacheEnabled(true);
		mControlPanel.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		
		addMarker();
		bindPanleListener();
		setAnimation();
	}
	//41.149100,121.122411料工
//40.828350,121.073950,笔架山
	//40.906588,121.207287 世博园
	//41.131990,121.147810 辽沈纪念馆

	private void addMarker() {
		
		Marker marker = new Marker(mLontitude,lLontutide,
				convertViewToBitmap(getMarkerLayout(text))){
			@Override
			public void onClick(float arg0, float arg1) {
				// TODO Auto-generated method stub
				//使用街景id进入街景
				//14071122130717101117700料工
//				//140731X5130720142327600笔架山
				//140730X5130721150440800世博园
				//140731X5130719131457200辽沈纪念馆
				mPanorama.setPosition(id_jiejing);
				ToastUtil.showLongToast(PanoramaActivity.this, "欢迎进入"+text);
				super.onClick(arg0, arg1);
			}

			@Override
			public float onGetItemScale(double arg0, float arg1) {
				// TODO Auto-generated method stub
				//Log.i("marker", "distance:" + arg0 +"; angleScale:" + arg1);
				return super.onGetItemScale(0.2*arg0, arg1);
			}
		};
		mPanorama.addMarker(marker);
		
//		Marker marker3 = new Marker(39.984548, 116.309579);
//		mPanorama.addMarker(marker3);
	}
	
	private void bindPanleListener() {
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				controlVanish.reset();
				controlVanish.start();
			}
		};

	}


	private void setAnimation() {
		controlAppear = AnimationUtils.loadAnimation(this, R.anim.control_appear);
		controlAppear.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				mControlPanel.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				if (isStreetViewPanoramaFinish()) {
					mControlPanel.setAnimation(controlVanish);
				}
			}
		});

		controlVanish = AnimationUtils.loadAnimation(PanoramaActivity.this, 
				R.anim.control_vanish);
		controlVanish.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mControlPanel.setVisibility(View.GONE);
			}
		});

	}

	private LinearLayout getMarkerLayout(String title) {
		LayoutInflater layInflater = getLayoutInflater();
		LinearLayout markerLayout = (LinearLayout)layInflater
				.inflate(R.layout.marker, null);
		TextView tvMarkerTitle = (TextView)markerLayout
				.findViewById(R.id.marker_title);

		tvMarkerTitle.setText(title);
		return markerLayout;
	}

	private Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), 
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	private void setStreetViewPanoramaState(boolean state) {
		streetViewPanoramaState = state;
	}
	
	private boolean isStreetViewPanoramaFinish() {
		return streetViewPanoramaState;
	}
	
	@Override
	public void OnStreetViewPanoramaFinish(boolean arg0) {
		// TODO Auto-generated method stub
		Log.i("change", "panorama finish");
		setStreetViewPanoramaState(true);
		mControlPanel.startAnimation(controlVanish);
	}

	@Override
	public void onStreetViewPanoramaChange(String arg0) {
		// TODO Auto-generated method stub
		setStreetViewPanoramaState(false);
		if (mControlPanel.getVisibility() != View.GONE) {
			return;
		}
		mControlPanel.startAnimation(controlAppear);
	}

	@Override
	public void onStreetViewPanoramaCameraChange(final StreetViewPanoramaCamera arg0) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mControlPanel.getVisibility() == View.GONE) {
					mControlPanel.startAnimation(controlAppear);
				} else {
					controlVanish.reset();
					controlVanish.start();
				}
//
			}
		});
	}

}
