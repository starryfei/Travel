package cn.cyansoft.contest.ditu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import cn.cyansoft.contest.R;


public class DituActivity extends Activity implements View.OnClickListener{

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Context context;
    private PopupMenu popMenu;
    // 定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongtitude;
    // private String tag;
    //自定义定位图标
    private BitmapDescriptor mIconLocation;
    // 覆盖物相关
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerLy;

    //赞
    private ImageView imageView_zan;
    private TextView textView_zan;
    private ImageView image;

    private LinearLayout Back;    //返回键
    private LinearLayout HeadRight;
    private View Head;
    private TextView HeadTitle;  //标题
    private ImageView Share;   //更多

    private LinearLayout mLlLike;
    private ImageView mIvLike;
    private ImageView zzz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_NEEDS_MENU_KEY);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.ditu);

        Head = findViewById(R.id.title_bar);
        HeadTitle = (TextView) Head.findViewById(R.id.head_title);
        HeadTitle.setText("百度地图");
        Back = (LinearLayout) findViewById(R.id.head_back);
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(this);
        HeadRight = (LinearLayout) Head.findViewById(R.id.head_share);
        HeadRight.setClickable(false);
        Share = (ImageView) Head.findViewById(R.id.share);
//        Share.setVisibility(View.INVISIBLE);
        Share.setBackgroundResource(R.drawable.head_menu_btn);
        Share.setOnClickListener(this);
        this.context = this;

        initView();
        //初始化定位
        initLocation();
        //覆盖物
        initMarker();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker)
            {
                Bundle extraInfo = marker.getExtraInfo();
                Info info = (Info) extraInfo.getSerializable("info");
                ImageView iv = (ImageView) mMarkerLy
                        .findViewById(R.id.id_info_img);
                TextView distance = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_distance);
                TextView name = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_name);
                TextView zan = (TextView) mMarkerLy
                        .findViewById(R.id.id_info_zan);



                iv.setImageResource(info.getImgId());
                distance.setText(info.getDistance());
                name.setText(info.getName());
                zan.setText(info.getZan() + "");

                //   InfoWindow infoWindow;
                TextView tv = new TextView(context);
                tv.setBackgroundResource(R.drawable.location_tips);
                tv.setPadding(30, 20, 30, 50);
                tv.setText(info.getName());
                tv.setTextColor(Color.parseColor("#ffffff"));

                final LatLng latLng = marker.getPosition();
                Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener()
        {

            @Override
            public boolean onMapPoiClick(MapPoi arg0)
            {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0)
            {
                mMarkerLy.setVisibility(View.GONE);
                mBaiduMap.hideInfoWindow();
            }
        });
    }

    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        mMarkerLy = (RelativeLayout) findViewById(R.id.id_maker_ly);
    }


    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();

        //设置地图放大比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        zzz= (ImageView) findViewById(R.id.zan);
        zzz.setImageResource(R.drawable.impression_detail_like_nor);
        zzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zzz.setImageResource(R.drawable.impression_detail_like_sel);
            }
        });
    }
    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

        //初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        // 开启方向传感器
        //  myOrientationListener.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        // 停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // 停止方向传感器
        // myOrientationListener.stop();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_map_common:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.id_map_site:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
//            case R.id.id_map_traffic:
//                if (mBaiduMap.isTrafficEnabled()) {
//                    mBaiduMap.setTrafficEnabled(false);
//                    item.setTitle("实时交通off");
//                } else {
//                    mBaiduMap.setTrafficEnabled(true);
//                    item.setTitle("实时交通on");
//                }
//                break;
            case R.id.id_map_my:
                centerToMyLocation();
                break;
            case R.id.id_add_overlay:
                addOverlays(Info.infos);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == Back) {
            this.finish();
        }
        if (v == Share) {
            popMenu = new PopupMenu(context, Share);
            getMenuInflater().inflate(R.menu.main, popMenu.getMenu());

            popMenu.show();

            popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // TODO Auto-generated method stub
                    switch (item.getItemId()) {
                        case R.id.id_map_common:
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                            break;
                        case R.id.id_map_site:
                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                            break;
//                        case R.id.id_map_traffic:
//                            if (mBaiduMap.isTrafficEnabled()) {
//                                item.setTitle("交通地图");
//                            }
//                            break;
                        case R.id.id_map_my:
                            centerToMyLocation();
                            break;
                        case R.id.id_add_overlay:
                            addOverlays(Info.infos);
                            break;
                    }
                    return false;

//                    return super.onOptionsItemSelected(item);
                }
            });
        }
    }

    private class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {

            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);

            //设置自定义图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.
                    LocationMode.NORMAL, true, mIconLocation);
            mBaiduMap.setMyLocationConfigeration(configuration);

            // 更新经纬度
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
//
//            System.out.println("经度坐标:" + mLatitude);
//            System.out.println("纬度坐标:" + mLongtitude);

            if (isFirstIn)
            {
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;

                Toast.makeText(context, location.getAddrStr(),
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
    /**
     * 添加覆盖物
     *
     * @param infos
     */
    private void addOverlays(final List<Info> infos)
    {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        MarkerOptions options;
        for (Info info : infos)
        {
            // 经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            options = new MarkerOptions().position(latLng).icon(mMarker)
                    .zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);

    }

    //地位到我的位置
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
//        Log.i(tag, "经度坐标:" + mLatitude);
//        Log.i(tag, "纬度坐标:" + mLongtitude);

    }


}