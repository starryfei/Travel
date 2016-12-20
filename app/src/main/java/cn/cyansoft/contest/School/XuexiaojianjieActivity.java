package cn.cyansoft.contest.School;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.cyansoft.contest.R;

public class XuexiaojianjieActivity extends Activity implements View.OnClickListener {
    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private View Head;

    private ImageButton play  ;
    private ImageButton pause  ;
    private ImageButton stop  ;
    private MediaPlayer myMediaPlayer ;
    private boolean playFlag = true ;	// 播放标记
    private boolean pauseFlag = false ;	// 暂停标记
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_xuexiaojianjie);

//        this.play = (ImageButton) super.findViewById(R.id.play) ;
//        this.pause = (ImageButton) super.findViewById(R.id.pause) ;
//        this.stop = (ImageButton) super.findViewById(R.id.stop) ;
//        this.play.setOnClickListener(new PlayOnClickListener()) ;
//        this.pause.setOnClickListener(new PauseOnClickListener()) ;
//        this.stop.setOnClickListener(new StopOnClickListener()) ;



        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("学校简介");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        Back.setOnClickListener(this);

    }
    private class PlayOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            myMediaPlayer = MediaPlayer.create(
                    XuexiaojianjieActivity.this, R.raw.yese);	// 要播放的文件
            if (myMediaPlayer != null) {
                myMediaPlayer.stop(); // 停止操作
                play.setClickable(false);
            }

            myMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                @Override
                public void onCompletion(MediaPlayer mp) {
                    playFlag = false ;	// 播放完毕
                    myMediaPlayer.release() ;	// 释放资源
                    play.setClickable(true);
                }}) ;
            try {
                myMediaPlayer.prepare() ;
                myMediaPlayer.start() ;
            }
            catch (Exception e) {
            }

        }
    }

    private class PauseOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(XuexiaojianjieActivity.this.myMediaPlayer != null) {
                if (pauseFlag) { // 现在暂停
                    myMediaPlayer.start();
                    pauseFlag = false ;
                } else {
                    myMediaPlayer.pause(); // 暂停
                    pauseFlag = true ;
                }
            }
        }

    }
    private class StopOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(myMediaPlayer != null) {
                myMediaPlayer.stop() ;	// 停止
                play.setClickable(true);
            }
        }
    }

    protected void onDestroy() {
        if(myMediaPlayer != null){
            if(myMediaPlayer.isPlaying()){
                myMediaPlayer.stop();
            }
            myMediaPlayer.release();
        }
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        if(v == Back){
            this.finish();
        }
    }
}
