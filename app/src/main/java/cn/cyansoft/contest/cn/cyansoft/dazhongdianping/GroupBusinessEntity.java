package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import java.io.Serializable;

public class GroupBusinessEntity implements Serializable {

	private String name;		//商户名
	private int id;				//商户ID
	private String city;		//商户所在城市
	private float latitude;		//商户所在经度
	private float longitude;	//商户所在纬度
	private String url;			//商户页链接
	private String h5_url;		//商户页链接(h5)
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getH5_url() {
		return h5_url;
	}
	public void setH5_url(String h5_url) {
		this.h5_url = h5_url;
	}
	
	
	
}
