package cn.cyansoft.contest.School;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.EXPO.TravelLineItem;
import cn.cyansoft.contest.EXPO.TravelLineListAdapter;
import cn.cyansoft.contest.R;

public class SchoolActivity extends BaseActivity implements View.OnClickListener{

    private View mBack;
    private TextView mTvTitle;
    private View mExtension;
    private ImageView mIvExtension;
    private View mTitleBar;
    private ListView mLv;
    private List<TravelLineItem> itemList;
    private TravelLineListAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        mTitleBar = findViewById(R.id.school_title_bar);
        mBack = mTitleBar.findViewById(R.id.head_back);
        mTvTitle = (TextView) mTitleBar.findViewById(R.id.head_title);
        mExtension = mTitleBar.findViewById(R.id.head_share);
        mExtension.setClickable(false);
        mIvExtension = (ImageView) mTitleBar.findViewById(R.id.share);
        mIvExtension.setVisibility(View.INVISIBLE);
        mLv = (ListView) findViewById(R.id.school_travel_line_list);

        initData();
        initEvent();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTvTitle.setText("辽工详情");
        TravelLineItem First = new TravelLineItem();
        First.setPic_id(R.drawable.xuexiaojianjie);
        First.setTitle("学校简介");
        First.setDesc(getString(R.string.xuexiaojianjie));

        TravelLineItem Second = new TravelLineItem();
        Second.setPic_id(R.drawable.xiaobiao);
        Second.setTitle("校标校训");
        Second.setDesc(getString(R.string.xiaobiaoxiaoxun));

//        TravelLineItem Third = new TravelLineItem();
//        Third.setPic_id(R.drawable.xianrenlingdao5);
//        Third.setTitle("现任领导");
//        Third.setDesc(getString(R.string.xianrenlingdao));

        TravelLineItem Fourth = new TravelLineItem();
        Fourth.setPic_id(R.drawable.jiaoxuelou);
        Fourth.setTitle("学校建筑");

        TravelLineItem Seventh = new TravelLineItem();
        Seventh.setPic_id(R.drawable.tushuguan);
        Seventh.setTitle("图书馆");

        TravelLineItem Eighth = new TravelLineItem();
        Eighth.setPic_id(R.drawable.xiaoyuanwenhua);
        Eighth.setTitle("校园文化");

        itemList = new ArrayList<TravelLineItem>();
        itemList.add(First);
        itemList.add(Second);
//        itemList.add(Third);
        itemList.add(Fourth);
        itemList.add(Seventh);
        itemList.add(Eighth);

        if (mListAdapter == null) {
            mListAdapter = new TravelLineListAdapter(itemList, SchoolActivity.this);
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
                    Intent intent = new Intent(SchoolActivity.this, XuexiaojianjieActivity.class);
                    startActivity(intent);
                }else if (position == 1) {
                    Intent intent = new Intent(SchoolActivity.this, XiaobiaoxiaoxunActivity.class);
                    startActivity(intent);
                }
//                }else if (position == 2) {
//                    Intent intent = new Intent(SchoolActivity.this, XianrenlingdaoActivity.class);
//                    startActivity(intent);
//                }
                else if (position == 2) {
                    Intent intent = new Intent(SchoolActivity.this, ShidajiaoxuelouActivity.class);
                    startActivity(intent);
                }else if (position == 3) {
                    Intent intent = new Intent(SchoolActivity.this, TushuguanActivity.class);
                    startActivity(intent);
                }else if (position == 4) {
                    Intent intent = new Intent(SchoolActivity.this, SchoolWenhua.class);
                    startActivity(intent);
                    SchoolActivity.this.finish();
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
}
