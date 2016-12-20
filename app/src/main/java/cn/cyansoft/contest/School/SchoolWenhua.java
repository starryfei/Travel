package cn.cyansoft.contest.School;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import cn.cyansoft.contest.R;
import cn.cyansoft.contest.xiaoyuanwenhua.SN;

public class SchoolWenhua extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_xiaoyuanwenhau);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container,new SN())
                    .commit();
        }
    }
}
