package cn.cyansoft.contest.xiaoyuanwenhua;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.cyansoft.contest.BaseFragment;
import cn.cyansoft.contest.R;
import cn.cyansoft.contest.School.SchoolActivity;
import cn.cyansoft.contest.School.SchoolWenhua;

public class GBZ extends Fragment implements View.OnClickListener{
    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private View Head;
    private Button saien, guangbozhan, dianshitai, tansuowang, xiaobao;
    private PopupWindow popupwindow;
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_gbz,container,false);

        Back = (LinearLayout)view.findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = view.findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("广播站");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);
        Share.setBackgroundResource(R.drawable.bucea_actionbar_more);
        Back.setOnClickListener(this);
        HeadRight.setClickable(false);

        webView = (WebView)view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl("http://v.youku.com/v_show/id_XNTc0MzU5Njk2.html?x");
        view.findViewById(R.id.up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new DST()).commit();
            }
        });
        view.findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new TSW()).commit();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == Back)
        {
            Intent intent = new Intent(getActivity(),SchoolActivity.class);
            startActivity(intent);
            this.getActivity().finish();
        }
    }
}
