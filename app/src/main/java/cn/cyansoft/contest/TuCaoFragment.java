package cn.cyansoft.contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.EXPO.TravelLineItem;
import cn.cyansoft.contest.EXPO.TravelLineListAdapter;


/**
 * Created by Administrator on 2016/3/10.
 */


public class TuCaoFragment extends BaseFragment implements View.OnClickListener {

    private View mBack;
    private TextView mTvTitle;
    private View mExtension;
    private ImageView mIvExtension;
    private View mTitleBar;
    private ListView mLv;
    private List<TravelLineItem> itemList;
    private TravelLineListAdapter mListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.activity_tucao, null);
        mLv = (ListView) view.findViewById(R.id.school_travel_line_list);
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
        TravelLineItem First = new TravelLineItem();
        First.setPic_id(R.drawable.tuexpo);
        First.setTitle("吐槽世博园");
        First.setDesc(getString(R.string.tucaoexpo));

        TravelLineItem Second = new TravelLineItem();
        Second.setPic_id(R.drawable.tuliaogong);
        Second.setTitle("吐槽辽工");
        Second.setDesc(getString(R.string.tucaoliaogong));

        TravelLineItem Third = new TravelLineItem();
        Third.setPic_id(R.drawable.tuliaos);
        Third.setTitle("吐槽辽沈战役纪念馆");
        Third.setDesc(getString(R.string.tucaoliaoshen));

        itemList = new ArrayList<TravelLineItem>();
        itemList.add(First);
        itemList.add(Second);
        itemList.add(Third);
        if (mListAdapter == null) {
            mListAdapter = new TravelLineListAdapter(itemList, getActivity());
            mLv.setAdapter(mListAdapter);
        }else {
            mListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initEvent() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if ( position == 0) {
                    Intent intent = new Intent(getActivity(), ExpoTuActivity.class);
                    startActivity(intent);
                }else if (position == 1) {
                    Intent intent = new Intent(getActivity(), TuLiaoGongActivity.class);
                    startActivity(intent);
                }else if (position == 2) {
                    Intent intent = new Intent(getActivity(), TuLiaoShenActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
