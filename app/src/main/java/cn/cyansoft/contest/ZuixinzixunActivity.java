package cn.cyansoft.contest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class ZuixinzixunActivity  extends Activity implements View.OnClickListener {
    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多
    private View Head;
    private ListView List_View;
    private MyAdpter myAdpter;
    private ImageView showImage;
    private Context context;
    private static List<Zuixinzixun> zuixinzixuns = new ArrayList<Zuixinzixun>();
    private List<String> list;
    private MyListView lv;
    private LvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.zixun_fragment);
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
        this.context =this;
        Bmob.initialize(this, "493171b6a420b75334fc69d6532c7a92");

        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("最新旅游资讯");
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
        Share.setVisibility(View.INVISIBLE);

        List_View = (ListView) findViewById(R.id.list_view);
        myAdpter = new MyAdpter(this);
        List_View.setAdapter(myAdpter);
        Back.setOnClickListener(this);
        showImage = (ImageView) findViewById(R.id.new_Image);
        List_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ZuixinzixunActivity.this, New_DetialActivity.class);
                String number = zuixinzixuns.get(position-1).getObjectId();
                intent.putExtra("postion", number);
                startActivity(intent);
            }
        });
        select();
    }

    private void select() {
        BmobQuery<Zuixinzixun> Query = new BmobQuery<Zuixinzixun>();
        Zuixinzixun zuixinzixun = new Zuixinzixun();
        String n = zuixinzixun.getObjectId();
        Query.getObject(ZuixinzixunActivity.this, n, new GetListener<Zuixinzixun>() {
            @Override
            public void onSuccess(Zuixinzixun zuixinzixun) {
                BmobFile icon = zuixinzixun.getImage();
                //我们可以直接获取到这个图片的Url，但是毕竟是一张图片，我们就直接设置在控件上
                 String url = icon.getFileUrl(context);
                icon.loadImage(ZuixinzixunActivity.this, showImage);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        Query.getObject(ZuixinzixunActivity.this, n, new GetListener<Zuixinzixun>() {
            @Override
            public void onSuccess(Zuixinzixun zuixinzixun) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
        Query.order("-createdAt");
        Query.findObjects(ZuixinzixunActivity.this, new FindListener<Zuixinzixun>() {
//
            @Override
            public void onSuccess(List<Zuixinzixun> list) {
                zuixinzixuns = list;
                myAdpter.notifyDataSetChanged();

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



    //内部类创建listView适配器
    public static class MyAdpter extends BaseAdapter {

        private LayoutInflater Inflater;
        private Context mContext;

        public MyAdpter(Context mContext){
            this.mContext = mContext;
            Inflater = LayoutInflater.from(mContext);

        }
        public static class ViewHolder {
            TextView tTitle;
            TextView tContent;
            TextView tTime;
            ImageView tImage;
        }

        @Override
        public int getCount() {
            return zuixinzixuns.size();
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
            final ViewHolder holder;
            if (convertView == null) {
                convertView = Inflater.inflate(R.layout.zuixin_adapter, null);
                holder = new ViewHolder();
                holder.tTitle = (TextView) convertView.findViewById(R.id.T_title);
                holder.tContent = (TextView) convertView.findViewById(R.id.T_context);
                holder.tTime = (TextView) convertView.findViewById(R.id.T_time);
//                holder.tImage = (ImageView) convertView.findViewById(R.id.new_Image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Zuixinzixun com = zuixinzixuns.get(position);
            //获取标题
            String str = com.getTitle();
            //获取简单内容
            String str1 = com.getContext();
            String set = com.getCreatedAt();
            BmobFile sr = com.getImage();
            //判断用户名是否为空

                holder.tTitle.setText(str);
                holder.tContent.setText(str1);
                holder.tTime.setText(set);
//            holder.tImage.setImageResource(R.drawable.news);

            return convertView;
        }
    }
}
