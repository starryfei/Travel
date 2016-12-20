package cn.cyansoft.contest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LiaoshenActivity extends Activity implements View.OnClickListener {
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
        setContentView(R.layout.activity_liaoshenzhanyi);

        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("辽沈战役纪念馆");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        Back.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://wapbaike.baidu.com/view/17334.htm?fr=aladdin&ref=wise&ssid=0&from=1011267h&uid=0&pu=usm@6,sz@1320_1004,ta@iphone_2_4.3_11_2.1&bd_page_type=1&baiduid=11F5B4C3F8D3305F9F33E6A38B80FFB7&tj=Xv_1_0_10_title");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                // t_title.setText(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == Back){
            this.finish();
        }
    }
}
