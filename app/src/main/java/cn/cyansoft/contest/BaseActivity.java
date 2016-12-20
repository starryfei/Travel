package cn.cyansoft.contest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/1.
 */
public abstract class BaseActivity extends FragmentActivity {

    protected int mScreenWidth;
    protected int mScreenHeight;
    protected float mDensity;
    protected LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        //获取屏幕属性
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mDensity = metric.density;

        mInflater = LayoutInflater.from(this);

        //NO_TITLE
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     *
     * 功能描述：初始化页面
     *
     */
    protected abstract void initView();

    /**
     * 功能描述：初始化数据
     */
    protected abstract void initData();

    /**
     * 功能描述：初始化各个响应事件
     */
    protected abstract void initEvent();

    /**
     * 功能描述：短Toast，参数为资源id
     */
    protected void showShortToast(int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    /**
     * 功能描述：短Toast，参数为String
     */
    protected void showShortToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 功能描述：长Toast，参数为资源id
     */
    protected void showLongToast(int res) {
        Toast.makeText(this, res, Toast.LENGTH_LONG).show();
    }

    /**
     * 功能描述：长Toast，参数为String
     */
    protected void showLongToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

}
