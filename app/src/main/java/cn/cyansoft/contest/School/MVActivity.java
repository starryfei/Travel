package cn.cyansoft.contest.School;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.cyansoft.contest.R;

public class MVActivity extends Activity implements View.OnClickListener {
    private WebView webView;
    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private View Head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mv);

        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("辽工MV");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        Back.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://www.liaogong.cn/mobile.php");
    }

    @Override
    public void onClick(View v) {
        if(v == Back){
            this.finish();
        }
    }
}
