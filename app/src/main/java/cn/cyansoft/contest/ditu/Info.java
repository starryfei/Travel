package cn.cyansoft.contest.ditu;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.cyansoft.contest.R;

public class Info implements Serializable
{
    private static final long serialVersionUID = -1010711775392052966L;
    private double latitude;
    private double longitude;
    private int imgId;
    private String name;
    private String distance;
    private int zan;


    public static List<Info> getInfos() {
        return infos;
    }

    public static void setInfos(List<Info> infos) {
        Info.infos = infos;
    }

    public static List<Info> infos = new ArrayList<Info>();

    static
    {
        infos.add(new Info(41.135893,121.155113, R.drawable.liaos, "辽沈纪念馆",
                "距离209米", 255));
        infos.add(new Info(41.149149, 121.130437, R.drawable.liaogong, "辽宁工业大学",
                "距离897米", 430));
        infos.add(new Info(41.092714, 121.124289, R.drawable.bohai, "渤海大学",
                "距离249米", 365));
        infos.add(new Info(41.153094, 121.148881, R.drawable.liaoyi, "辽宁医学院",
                "距离679米",110));
        infos.add(new Info(40.834698, 121.080352, R.drawable.bijias, "笔架山",
                "距离679米",120));
        infos.add(new Info(41.170698, 121.067517, R.drawable.bpt, "北普陀山",
                "距离679米",180));
        infos.add(new Info(40.917179,121.212468, R.drawable.expo, "锦州世博园",
                "距离679米",666));
    }

    public Info(double latitude, double longitude, int imgId, String name,
                String distance ,int zan )
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgId = imgId;
        this.name = name;
        this.distance = distance;
        this.zan = zan;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public int getImgId()
    {
        return imgId;
    }

    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public int getZan()
    {
        return zan;
    }

    public void setZan(int zan)
    {
        this.zan = zan;
    }

}
