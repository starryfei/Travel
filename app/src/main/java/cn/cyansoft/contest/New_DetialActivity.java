package cn.cyansoft.contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class New_DetialActivity extends BaseActivity implements View.OnClickListener {
    private TextView t_title;
    private TextView t_content;
    private String no;
    private String mContent;
    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private View Head;
    private  ImageView imag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_fragment);

        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(this);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("最新旅游资讯");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        Bmob.initialize(this, "493171b6a420b75334fc69d6532c7a92");
        t_title = (TextView) findViewById(R.id.t1);
        t_content = (TextView) findViewById(R.id.t2);
        imag = (ImageView) findViewById(R.id.image1);
        select();

    }

    public void select() {
        Intent intent = getIntent();
        no = intent.getStringExtra("postion");
        Zuixinzixun zuixinzixun = new Zuixinzixun();
        BmobQuery<Zuixinzixun> Query = new BmobQuery<Zuixinzixun>();
//        Query.addWhereEqualTo("objectid",no);
//        Query.order("-createdAt");

        Query.getObject(New_DetialActivity.this, no, new GetListener<Zuixinzixun>() {
            @Override
            public void onSuccess(Zuixinzixun zuixinzixun) {
                BmobFile icon = zuixinzixun.getImage();
                //我们可以直接获取到这个图片的Url，但是毕竟是一张图片，我们就直接设置在控件上
//                 String url = icon.getFileUrl(context);

                icon.loadImage(New_DetialActivity.this, imag);
                t_title.setText(zuixinzixun.getTitle());
                t_content.setText(zuixinzixun.getT_content());
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {

    }


    @Override
    public void onClick(View v) {
        if(v ==Back){
            this.finish();
        }
    }
}
