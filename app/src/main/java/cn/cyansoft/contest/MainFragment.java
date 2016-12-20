package cn.cyansoft.contest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.EXPO.ExpoActivity;
import cn.cyansoft.contest.EXPO.TravelLineItem;
import cn.cyansoft.contest.EXPO.TravelLineListAdapter;
import cn.cyansoft.contest.School.SchoolActivity;
import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.GrouponActivity;
import cn.cyansoft.contest.ditu.DituActivity;

/**
 * Created by Administrator on 2016/4/10 0010.
 */
public class MainFragment extends BaseFragment  {
    private GridView gridView;
    private Context mContext;
    private ViewPager mViewPager;
    private int[] imagesIds;
    private ArrayList<ImageView> images;
    private ViewPagerAdapter mAdapter;
    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem;//当前页面
    private List<TravelLineItem> itemList;
    private TravelLineListAdapter mListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_first_main, null);
        gridView=(MyGridView)view.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(mContext));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //wenhua
                if (position == 0) {
                    Intent intent = new Intent(mContext,PanoramaActivity.class);
                    startActivity(intent);
                }
                else if(position ==1 ){
                    Intent intent = new Intent(mContext, ZuixinzixunActivity.class);
                    startActivity(intent);
                }
                //世博园攻略
                else if (position == 2 ) {
                    Intent intent = new Intent(mContext, ExpoActivity.class);
                    startActivity(intent);
                }
                //辽沈战役纪念馆
                else if (position == 3  ) {
                    Intent intent = new Intent(mContext, LiaoshenActivity.class);
                    startActivity(intent);
                }
                //bijias
                else if(position == 4 ){
                    Intent intent = new Intent(mContext, BijiashanActivity.class);
                    startActivity(intent);
                }//putuos
                else if(position == 5 ){
                    Intent intent = new Intent(mContext, BeiputuoshanActivity.class);
                    startActivity(intent);
                }//学校
                else if (position == 6 ) {
                    Intent intent = new Intent(mContext, SchoolActivity.class);
                    startActivity(intent);
                }
                //地图
                else if (position == 7  ) {
                    Intent intent = new Intent(mContext, DituActivity.class);
                    startActivity(intent);
                }
                //团购信息
                else if(position == 8){
                    Intent intent = new Intent(mContext, GrouponActivity.class);
                    startActivity(intent);
                }//美食

            }
        });
        initData();
        initEvent();
        initBanner(view);
        return  view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    private void initBanner(View view) {
        mViewPager = (ViewPager)view.findViewById(R.id.guide_vp);
        // 图片id
        imagesIds = new int[] { R.drawable.a1, R.drawable.a2,
                R.drawable.a4, R.drawable.a3, R.drawable.bucea_guide_banner05 };

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

        dots.add(view.findViewById(R.id.dot_0));
        dots.add(view.findViewById(R.id.dot_1));
        dots.add(view.findViewById(R.id.dot_2));
        dots.add(view.findViewById(R.id.dot_3));
        dots.add(view.findViewById(R.id.dot_4));
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
