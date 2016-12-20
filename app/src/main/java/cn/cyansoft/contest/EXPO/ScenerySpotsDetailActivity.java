package cn.cyansoft.contest.EXPO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;

public class ScenerySpotsDetailActivity extends BaseActivity implements OnClickListener{

	private View mTitleBar;
	private View mBack;
	private TextView mTvTitle;
	private View mExtension;
	private ImageView mIvExtension;
	private GridView mGridView;
	private ScenerySpotsAdapter spotsAdapter;
	private ListView mLv;
	private String mCategory;
	private int mPosition;
	private ScenerySpotsDetailAdapter mSpotsDetailAdapter;
	private double mLatitude;
	private double mLongitude;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.bucea_scenery_spot_detail);
		initView();
		initData();
		initEvent();
	}

	@Override
	protected void initView() {
		mTitleBar = findViewById(R.id.scenery_spots_detail_title_bar);
		mBack = mTitleBar.findViewById(R.id.backarrow);
		mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
		mExtension = mTitleBar.findViewById(R.id.head_share);
		mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
		mExtension.setClickable(false);
		mIvExtension.setVisibility(View.INVISIBLE);
		mLv = (ListView) findViewById(R.id.lv_scenery_spots);
		mLv.setDivider(null);
	}

	@Override
	protected void initData() {
		Intent intent = getIntent();
		mCategory = intent.getStringExtra("category");
		mPosition = intent.getIntExtra("position", -1);

		List<ParagraphItem> paragraphItems = getParagraphListByPosition(mPosition);
		if (mSpotsDetailAdapter == null) {
			mSpotsDetailAdapter = new ScenerySpotsDetailAdapter(ScenerySpotsDetailActivity.this, paragraphItems);
			mLv.setAdapter(mSpotsDetailAdapter);
		}else {
			mSpotsDetailAdapter.notifyDataSetChanged();
		}

	}

	private List<ParagraphItem> getParagraphListByPosition(int position) {
		List<ParagraphItem> list = new ArrayList<ParagraphItem>();

		if ("celebrity".equals(mCategory)) {
			if (position == 0) {
				mTvTitle.setText("国际生态馆");

				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.guojishengtaiguan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.GUOJISHENGTAIGUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.guojishengtaiguan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.GUOJISHENGTAIGUAN_2);

				list.add(item1);
				list.add(item2);
				//初始化经纬度坐标
				mLatitude = Constants.LocParams.GJST_LATITUDE;
				mLongitude = Constants.LocParams.GJST_LONGITUDE;
			}else if (position == 1) {
				mTvTitle.setText("水韵之舞剧场");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.shuiyunzhiwujuchang_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.SHUIYUNZHIWUJUCHANG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.shuiyunzhiwujuchang_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.SHUIYUNZHIWUJUCHANG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.SYZWJC_LATITUDE;
				mLongitude = Constants.LocParams.SYZWJC_LONGITUDE;

			}else if (position == 2) {
				mTvTitle.setText("百花馆");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.baihuaguan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.BAIHUAGUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.baihuaguan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.BAIHUAGUAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.BHG_LATITUDE;
				mLongitude = Constants.LocParams.BHG_LONGITUDE;

			}else if (position == 3) {
				mTvTitle.setText("百花塔");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.baihuata_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.BAIHUATA_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.baihuata_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.BAIHUATA_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.BHT_LATITUDE;
				mLongitude = Constants.LocParams.BHT_LONGITUDE;

			}else if (position == 4) {
				mTvTitle.setText("海洋科技创意馆");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.haiyangkejichuangyiguan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.HAIYANGKEJICHUANGYIGUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.haiyangkejichuangyiguan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.HAIYANGKEJICHUANGYIGUAN_2);
				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.HYKJCYG_LATITUDE;
				mLongitude = Constants.LocParams.HYKJCYG_LONGITUDE;

			}else if (position == 5) {
				mTvTitle.setText("迷宫游苑");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.migongyouyuan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.MIGONGYOUYUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.migongyouyuan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.MIGONGYOUYUAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.MGYY_LATITUDE;
				mLongitude = Constants.LocParams.MGYY_LONGITUDE;

			}else if (position == 6) {
				mTvTitle.setText("演绎广场");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.yanyiguangchang_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.YANYIGUANGCHANG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.yanyiguangchang_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.YANYIGUANGCHANG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.YYGC_LATITUDE;
				mLongitude = Constants.LocParams.YYGC_LONGITUDE;

			}else if (position == 7) {
				mTvTitle.setText("海湾花田");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.haiwanhuatian_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.HAIWANHUATIAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.haiwanhuatian_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.HAIWANHUATIAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.HWHT_LATITUDE;
				mLongitude = Constants.LocParams.HWHT_LONGITUDE;

			}else if (position == 8) {
				mTvTitle.setText("牡丹芍药园");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.mudanshaoyaoyuan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.MUDANSHAOYAOYUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.mudanshaoyaoyuan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.MUDANSHAOYAOYUAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.MDSYY_LATITUDE;
				mLongitude = Constants.LocParams.MDSYY_LONGITUDE;

			}else if (position == 9) {
				mTvTitle.setText("百草园");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.baicaoyuan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.BAICAOYUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.baicaoyuan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.BAICAOYUAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.BCY_LATITUDE;
				mLongitude = Constants.LocParams.BCY_LONGITUDE;


			}else if (position == 10) {
				mTvTitle.setText("杏花园");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.xinghuayuan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.XINGHUAYUAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.xinghuayuan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.XINGHUAYUAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.XHY_LATITUDE;
				mLongitude = Constants.LocParams.XHY_LONGITUDE;


			}else if (position == 11) {
				mTvTitle.setText("张和国际树化石园");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.zhangheguojishuhuashiyuan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.ZHANGHEGUOJISHUHUASHIYUAN);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.zhangheguojishuhuashiyuan_txt1);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.ZHGJSHSY_LATITUDE;
				mLongitude = Constants.LocParams.ZHGJSHSY_LONGITUDE;

			}
		}else if ("scenery_spots".equals(mCategory)) {
			//特色景点
			if (position == 0) {
				mTvTitle.setText("荷兰");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.helan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.HELAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.helan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.HELAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.HL_LATITUDE;
				mLongitude = Constants.LocParams.HL_LONGITUDE;
			}else if (position == 1) {
				mTvTitle.setText("葡萄牙");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.putaoya_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.PUTAOYA_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.putaoya_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.PUTAOYA_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.PTY_LATITUDE;
				mLongitude = Constants.LocParams.PTY_LONGITUDE;

			}else if (position == 2) {
				mTvTitle.setText("哥伦比亚");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.gelunbiya_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.GELUNBIYA_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.gelunbiya_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.GELUNBIYA_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.GLBY_LATITUDE;
				mLongitude = Constants.LocParams.GLBY_LONGITUDE;
			}else if (position == 3) {
				mTvTitle.setText("西班牙");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.xibanya_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.XIBANYA_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.xibanya_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.XIBANYA_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.XBY_LATITUDE;
				mLongitude = Constants.LocParams.XBY_LONGITUDE;

			}else if (position == 4) {
				mTvTitle.setText("新西兰");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.xinxilan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.XINXILAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.xinxilan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.XINXILAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.XXL_LATITUDE;
				mLongitude = Constants.LocParams.XXL_LONGITUDE;

			}else if (position == 5) {
				mTvTitle.setText("伊朗");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.yliang_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.YILANG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.yliang_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.YILANG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 6) {
				mTvTitle.setText("墨西哥");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.moxige_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.MOXIGE_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.moxige_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.MOXIGE_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 7) {
				mTvTitle.setText("鞍山");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.anshan_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.ANSHAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.anshan_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.ANSHAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 8) {
				mTvTitle.setText("锦州");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.jinzhou_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.JINZHOU_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.jinzhou_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.JINZHOU_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 9) {
				mTvTitle.setText("本溪");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.benxi_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.BENXI_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.benxi_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.BENXI_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 10) {
				mTvTitle.setText("阜新");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.fuxin_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.FUXIN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.fuxin_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.FUXIN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 11) {
				mTvTitle.setText("铁岭");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.tieling_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.TIELING_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.tieling_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.TIELING_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 12) {
				mTvTitle.setText("葫芦岛");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.huludao_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.HULUDAO_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.huludao_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.HUKUDAO_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 13) {
				mTvTitle.setText("盘锦");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.panjing_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.PANJIN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.panjing_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.PANJIN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 14) {
				mTvTitle.setText("营口");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.yingkou_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.YINGKOU_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.yingkou_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.YINGKOU_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 15) {
				mTvTitle.setText("丹东");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.dandong_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.DANDONG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.dandong_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.DANDONG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 16) {
				mTvTitle.setText("大连");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.dalian_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.DALIAN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.dalian_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.DALIAN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 17) {
				mTvTitle.setText("沈阳");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.shenyang_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.SHENYANG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.shenyang_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.SHENYANG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
			else if (position == 18) {
				mTvTitle.setText("抚顺");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.fushun_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.FUSHUN_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.fushun_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.FUSHUN_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}else if (position == 19) {
				mTvTitle.setText("朝阳");
				ParagraphItem item1 = new ParagraphItem();
				item1.setParagraphId(R.string.chaoyang_txt);
				item1.setParagraphUrl(Constants.ScenerySpotParams.CHAOYANG_1);

				ParagraphItem item2 = new ParagraphItem();
				item2.setParagraphId(R.string.chaoyang_txt1);
				item2.setParagraphUrl(Constants.ScenerySpotParams.CHAOYANG_2);

				list.add(item1);
				list.add(item2);

				//初始化经纬度坐标
				mLatitude = Constants.LocParams.FX_LATITUDE;
				mLongitude = Constants.LocParams.FX_LONGITUDE;
			}
		}
		return list;

	}

	@Override
	protected void initEvent() {
		mBack.setOnClickListener(this);
		mExtension.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view == mBack) {
			this.finish();
		}
// else if (view == mExtension) {
//			Intent intent = new Intent(ScenerySpotsDetailActivity.this,CommonMapActivity.class);
//			intent.putExtra("latitude", mLatitude);
//			intent.putExtra("longitude", mLongitude);
//			startActivity(intent);
//		}
	}

}
