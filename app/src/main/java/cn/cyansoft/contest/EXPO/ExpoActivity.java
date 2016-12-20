package cn.cyansoft.contest.EXPO;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.R;


public class ExpoActivity extends BaseActivity implements View.OnClickListener {
    private View mBack;
    private TextView mTvTitle;
    private View mExtension;
    private ImageView mIvExtension;
    private View mTitleBar;
    private ListView mLv;
    private ViewPager mViewPager;
    private int[] imagesIds;
    private ArrayList<ImageView> images;
    private Context mContext = ExpoActivity.this;
    private ViewPagerAdapter mAdapter;
    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem;//当前页面
    private List<TravelLineItem> itemList;
    private TravelLineListAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expo_activity);

        mTitleBar = findViewById(R.id.travel_line_title_bar);
        mBack = mTitleBar.findViewById(R.id.head_back);
        mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
        mExtension = mTitleBar.findViewById(R.id.head_share);
        mExtension.setClickable(false);
        mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
        mIvExtension.setVisibility(View.INVISIBLE);
        mLv = (ListView) findViewById(R.id.lv_travel_line_list);
        initBanner();

        initData();
        initEvent();
}

    @Override
    protected void initView() {

    }
    private void initBanner() {
        mViewPager = (ViewPager) findViewById(R.id.guide_vp);
        // 图片id
        imagesIds = new int[] { R.drawable.bucea_guide_banner01, R.drawable.bucea_guide_banner02,
                R.drawable.bucea_guide_banner03, R.drawable.bucea_guide_banner04, R.drawable.bucea_guide_banner05 };

        // 显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imagesIds.length; i++) {
            ImageView imageView = new ImageView(mContext );
            imageView.setBackgroundResource(imagesIds[i]);
//			MyImageLoader.getInstance(mContext).displayImage("drawable://"+imagesIds[i], imageView);
            images.add(imageView);
        }
        // 显示的点
        final ArrayList<View> dots = new ArrayList<View>();

        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        mAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 页面选择了以后（页面滑动后执行）
             */
            @Override
            public void onPageSelected(int position) {
                dots.get(oldPosition).setBackgroundResource(R.drawable.bucea_dot_normal);
                dots.get(position).setBackgroundResource(R.drawable.bucea_dot_focused);
                oldPosition = position;

                currentItem = position;
            }


            /**
             * 页面滑动后（滑动了激活）
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            /**
             * 页面滑动状态生改变（页面滑动时执行）
             */
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    @Override
    protected void initData() {
        mTvTitle.setText("世博攻略");
        TravelLineItem celebrityItem = new TravelLineItem();
        celebrityItem.setPic_id(R.drawable.bucea_travel_line_featured_spots);
        celebrityItem.setTitle(getString(R.string.expo_nature_title));
        celebrityItem.setDesc(getString(R.string.expo_nature));

        TravelLineItem featuredItem = new TravelLineItem();
        featuredItem.setPic_id(R.drawable.bucea_travel_line_celebrity);
        featuredItem.setTitle(getString(R.string.expo_city_title));
        featuredItem.setDesc(getString(R.string.expo_city));

        TravelLineItem trafficItem = new TravelLineItem();
        trafficItem.setPic_id(R.drawable.bucea_travel_line_traffic_guide);
        trafficItem.setTitle(getString(R.string.expo_travel_title));
        trafficItem.setDesc(getString(R.string.expo_travel));

        itemList = new ArrayList<TravelLineItem>();
        itemList.add(celebrityItem);
        itemList.add(featuredItem);
        itemList.add(trafficItem);
        if (mListAdapter == null) {
            mListAdapter = new TravelLineListAdapter(itemList, ExpoActivity.this);
            mLv.setAdapter(mListAdapter);
        }else {
            mListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initEvent() {
        mBack.setOnClickListener(this);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if ( position == 0) {
                    Intent intent = new Intent(ExpoActivity.this, ScenerySpotsActivity.class);
                    intent.putExtra("category", "celebrity");
                    startActivity(intent);
                }else if (position == 1) {
                    Intent intent = new Intent(ExpoActivity.this, ScenerySpotsActivity.class);
                    intent.putExtra("category", "scenery_spots");
                    startActivity(intent);
                }else if (position == 2) {
                    Intent intent = new Intent(ExpoActivity.this, TrafficGuideActivity.class);
                    intent.putExtra("category", "scenery_spots");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            this.finish();
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        /**
         * 可以看得见的当前页面（view） 当前显示的这张图片是在指定的view上进行显示吗，他们显示的图片是同一张图片吗
         * 如果是同一张图片就返回真
         * view表示页面上要显示的图片，object代表当前的对象 也是图片
         * 如果当前这两者所要显示的图片是同一张图片，就证明就是我们所看到要显示的图片
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
//			super.destroyItem(container, position, object);
            //将当前的view
            container.removeView(images.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            container.addView(images.get(position));
            return images.get(position);
        }

    }
}
