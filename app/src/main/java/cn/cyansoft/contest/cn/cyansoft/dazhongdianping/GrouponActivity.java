package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.KeyEvent;

import cn.cyansoft.contest.BaseActivity;
import cn.cyansoft.contest.Constants;
import cn.cyansoft.contest.R;

public class GrouponActivity extends BaseActivity {

    private static final int GROUPON_FRAGMENT_TAG = 0;
    private static final int BUSINESS_DETAIL_FRAGMENT = 1;
    private Context mContext = GrouponActivity.this;
    private int mCurrentF;//标记当前展示的Fragments
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.groupon_activity);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mFragmentManager=getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(
                R.id.group_list, new GrouponListFragment(),
                Constants.FragmentTag.GROUPON_FRAGMENT_TAG)
                .commit();
        mCurrentF = GROUPON_FRAGMENT_TAG;
    }

    @Override
    protected void initEvent() {
        // TODO Auto-generated method stub

    }

    /**
     * 切换至商户详情页
     * @param bundle
     */
    public void changeFragment(Bundle bundle) {
        BusinessDetailFragment detailFragment = new BusinessDetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.group_list,
                detailFragment, Constants.FragmentTag.BUSINESS_DETAIL_FRAGMENT_TAG).
                addToBackStack(Constants.FragmentTag.BUSINESS_DETAIL_FRAGMENT_TAG)
                .commit();
        mCurrentF = BUSINESS_DETAIL_FRAGMENT;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){//判断是否是后退键
            if(mCurrentF == BUSINESS_DETAIL_FRAGMENT){
                Fragment f = mFragmentManager.findFragmentByTag(Constants.FragmentTag.BUSINESS_DETAIL_FRAGMENT_TAG);
                if(f != null && f instanceof BusinessDetailFragment){
                    ((BusinessDetailFragment)f).webviewGoBack();
                }
            }else {
                GrouponActivity.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
