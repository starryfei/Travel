package cn.cyansoft.contest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class ExpoTuActivity extends Activity implements View.OnClickListener {
        private LinearLayout Back;    //返回键
        private LinearLayout HeadRight;
        private TextView HeadTitle;  //标题
        private ImageView Share;   //更多
        private View Head;
        private Button btn;
        private ListView listView_Comment;
        private EditText ed_comment;
        private MyAdpter myAdpter;
        private static List<expo> expos = new ArrayList<expo>();
        private List<String> list;
        private MyListView lv;
        private LvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_expo_tu);

        lv = (MyListView) findViewById(R.id.list_view);
        list = new ArrayList<String>();
        adapter = new LvAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setonRefreshListener(new MyListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        list.add("刷新后添加的内容");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv.onRefreshComplete();
                    }
                }.execute(null, null, null);
            }
        });
        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("世博园吐槽栏");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        Back.setOnClickListener(this);
            Bmob.initialize(this, "493171b6a420b75334fc69d6532c7a92");

                ed_comment = (EditText) findViewById(R.id.et_content);
                listView_Comment = (ListView) findViewById(R.id.list_view);
                myAdpter = new MyAdpter(this);
                listView_Comment.setAdapter(myAdpter);
                  select();
                btn = (Button) findViewById(R.id.btn_publish);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {
                    submit();
                    select();
                }
            });

        }

    private void submit() {
            String content = ed_comment.getText().toString();
            expo ex = new expo();
            ex.setUserName(Constants.QQParams.Name);
        if(ex.getUserName()!=null) {
            if (content.equals("")) {
                return;
            }
            ex.setUserComment(content);
            ex.save(ExpoTuActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(ExpoTuActivity.this, "评论成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(ExpoTuActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                }
            });

        }else {

            Toast.makeText(ExpoTuActivity.this, "请登录之后再进行评论!",Toast.LENGTH_SHORT).show();
            ed_comment.setText("");
        }
    }

    private void select() {
        BmobQuery<expo> Query = new BmobQuery<expo>();
        Query.order("-createdAt");
        Query.findObjects(ExpoTuActivity.this, new FindListener<expo>() {
            @Override
            public void onSuccess(List<expo> list) {
                expos = list;
                myAdpter.notifyDataSetChanged();
                ed_comment.setText("");
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == Back){
            this.finish();
        }
    }
//    //获取当前评论时间
//    public static String getTime(){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm;ss");
//        Date curDate = new Date();
//        String str = format.format(curDate);
//        return  str;
//    }

    //内部类创建listView适配器
    public static class MyAdpter extends BaseAdapter {

        private LayoutInflater Inflater;
        private Context mContext;

        public MyAdpter(Context mContext){
            this.mContext = mContext;
            Inflater = LayoutInflater.from(mContext);

        }
        public static class ViewHolder {
            TextView tName;
            TextView tContent;
            TextView t_time;

        }

        @Override
        public int getCount() {
            return expos.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = Inflater.inflate(R.layout.select, null);
                holder = new ViewHolder();
                holder.tName = (TextView) convertView.findViewById(R.id.text_name);
                holder.tContent = (TextView) convertView.findViewById(R.id.text_content);
                holder.t_time = (TextView) convertView.findViewById(R.id.t_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            expo com = expos.get(position);
            //获取用户id
            String str = com.getUserName();
            //获取评论
            String str1 = com.getUserComment();
            String set = com.getCreatedAt();
            //判断用户名是否为空
            if(str !=null) {
                holder.tName.setText(str);
                holder.tContent.setText(str1);
                holder.t_time.setText(set);
            }

            return convertView;
        }
    }
}

