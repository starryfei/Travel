package cn.cyansoft.contest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.cyansoft.contest.QQ.LogUtil;
import cn.cyansoft.contest.QQ.QQUserInfoEntity;
import cn.cyansoft.contest.QQ.onGetUserInfoListener;
import cn.cyansoft.contest.Weibo.WeiboUserInfoEntity;
import cn.cyansoft.contest.Yingxiang.ImpressionFragment;
import cn.cyansoft.contest.Yingxiang.MyImpressionActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    //使用照相机拍照获取图片
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
    //从Intent获取图片路径的KEY
    public static final String KEY_PHOTO_PATH = "photo_path";


    /**
     * 获取到的图片路径
     */
    private String picPath;
    private Uri photoUri;

    private static final int GUIDE = 0;
    private static final int MAP = 1;
    private static final int IMPRESS = 2;
    private static final int MORE = 3;

    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private View Head;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private RadioButton Guide, Map, Impress, More;
    private RadioButton[] Buttons;
    private int ViewCount = 4;
    private int mCurSel;
    private onGetUserInfoListener getUserInfoListener;

    public void setGetUserInfoListener(onGetUserInfoListener getUserInfoListener) {
        this.getUserInfoListener = getUserInfoListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initView() {
        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.INVISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        Share = (ImageView) Head.findViewById(R.id.share);
        initFooterBar();
        changeFragment(GUIDE);
    }

    private void initFooterBar() {

        Guide = (RadioButton) findViewById(R.id.guide);
        Map = (RadioButton) findViewById(R.id.map);
        Impress = (RadioButton) findViewById(R.id.impress);
        More = (RadioButton) findViewById(R.id.more);

        Buttons = new RadioButton[ViewCount];
        LinearLayout llFooter = (LinearLayout) findViewById(R.id.foot);
        for (int i = 0; i < ViewCount; i++) {
            Buttons[i] = (RadioButton) llFooter.getChildAt(i * 2);
            Buttons[i].setTag(i);
            Buttons[i].setChecked(false);
            Buttons[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = (Integer) (v.getTag());
                    if (mCurSel == pos) {
                        return;
                    }
                    switch (pos) {
                        case 0:// 旅游攻略
                            changeFragment(GUIDE);
                            break;
                        case 1:// 地图导航
                            changeFragment(MAP);
                            break;
                        case 2:// 印象
                            changeFragment(IMPRESS);
                            break;
                        case 3:// 更多
                            changeFragment(MORE);
                            break;
                    }
                    setCurPoint(pos);
                }

            });
        }
        mCurSel = 0;
        Buttons[mCurSel].setChecked(true);
    }

    private void setCurPoint(int index) {
        if (index < 0 || index > ViewCount - 1 || mCurSel == index)
            return;

        Buttons[mCurSel].setChecked(false);
        Buttons[index].setChecked(true);
        mCurSel = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ViewCount == 0)
            ViewCount = 4;
        if (mCurSel == 0 && !Guide.isChecked()) {
            Guide.setChecked(true);
            Map.setChecked(false);
            Impress.setChecked(false);
            More.setChecked(false);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        HeadRight.setOnClickListener(this);
    }

    private void showSelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择照片");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        String[] items = new String[]{"使用相机拍照", "从相册选择"};
        int index = 1;
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                switch (which) {
                    case 0://相机拍照
                        takePhoto();
                        break;
                    case 1://从相册选择
                        pickPhoto();
                        break;
                }

                //关闭对话框
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            /**-----------------*/
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        }else{
            Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }
    //从相册里选取
    private void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    @Override
    public void onClick(View v) {
        if (v == HeadRight) {
            //分享
            if (mCurSel == 0) {
                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "f分享");
                share_intent.putExtra(Intent.EXTRA_TEXT, "Hi，推荐你使用一款软件：e游锦州自助服务系统");

                //创建选择器
                share_intent = Intent.createChooser(share_intent, "分享");
                startActivity(share_intent);
            }
            //打开相机
            else if (mCurSel == 2) {//拍照
                showSelectDialog();
            }
        }
    }

    public void changeFragment(int target) {
        if (target == GUIDE) {
            Share.setVisibility(View.VISIBLE);
            HeadRight.setClickable(true);
            HeadTitle.setText("e游锦州");
            Share.setBackgroundResource(R.drawable.head_guide_share);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.head, new MainFragment(),
                    Constants.FragmentTag.GUIDE_FRAGMENT_TAG)
                    .commit();
        } else if (target == MAP) {
            Share.setVisibility(View.INVISIBLE);
            HeadRight.setClickable(true);
            HeadTitle.setText("吐槽专栏");
            Share.setBackgroundResource(R.drawable.head_map_navigate);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.head, new TuCaoFragment(),
                    Constants.FragmentTag.MAP_FRAGMENT_TAG)
                    .commit();
        } else if (target == IMPRESS) {
            Share.setVisibility(View.VISIBLE);
            HeadRight.setClickable(true);
            HeadTitle.setText("个人印象");
            Share.setBackgroundResource(R.drawable.head_impress_camera);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.head, new ImpressionFragment(),
                    Constants.FragmentTag.IMPRESS_FRAGMENT_TAG)
                    .commit();
        } else if (target == MORE) {
            HeadTitle.setText("个人中心");
            Share.setVisibility(View.INVISIBLE);
            HeadRight.setClickable(false);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.head, new MoreFragment(),
                    Constants.FragmentTag.MORE_FRAGMENT_TAG)
                    .commit();
        }
    }


    /**
     * 当第三方登录时会将用户信息返回
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    doPhoto(requestCode, data);
                    break;
            }
        } else if (resultCode == Activity.RESULT_OK) {
            doPhoto(requestCode, data);
        }
    }

    private void doPhoto(int requestCode, Intent data) {
        if(requestCode == SELECT_PIC_BY_PICK_PHOTO ) { //从相册取图片，有些手机有异常情况，请注意
            if(data == null){
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if(photoUri == null ){
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);
        if(cursor != null ){
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);

            try {
                //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                if(Build.VERSION.SDK_INT < 14) {
                    cursor.close();
                }
            } catch (Exception e) {
                LogUtil.getInstance().e(e);
            }
        }
        LogUtil.getInstance().d("imagePath = "+picPath);
        if(picPath !=null){
            Intent intent = new Intent(MainActivity.this,MyImpressionActivity.class);
            intent.putExtra("pic_path", picPath);
            startActivity(intent);
        }else{
            Toast.makeText(this, "选择文件不正确!", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
        }
        return false;
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("e游锦州");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("是否退出当前应用？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }).setNegativeButton("取消", null);

        builder.show();

    }
}

