package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import java.io.Serializable;
import java.util.List;

import cn.cyansoft.contest.cn.cyansoft.dazhongdianping.GroupBusinessEntity;

public class GrouponEntity implements Serializable {

	private String deal_id;			//团购单ID
	private String title;			//团购标题
	private String description;		//团购描述
	private String city;			//城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
	private float list_price;		//团购包含商品原价值
	private float current_price;	//团购价格
	private List<String> regions;	//团购适用商户所在行政区
	private List<String> categories;//团购所属分类
	private int purchase_count;		//团购当前已购买数
	private String publish_date;	//团购发布上线日期
	private String purchase_deadline;//团购单的截止购买日期
	private int distance;			//团购单所适用商户中距离参数坐标点最近的一家与坐标点的距离，单位为米；如不传入经纬度坐标，结果为-1；如团购单无关联商户，结果为MAXINT
	private String image_url;		//团购图片链接，最大图片尺寸450×280
	private String s_image_url;		//小尺寸团购图片链接，最大图片尺寸160×100
	private String deal_url;		//团购Web页面链接，适用于网页应用
	private String deal_h5_url;		//团购HTML5页面链接，适用于移动应用和联网车载应用
	private float commission_ratio;	//当前团单的佣金比例
	private List<GroupBusinessEntity> businesses;//团购所适用的商户列表
	
	public String getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(String deal_id) {
		this.deal_id = deal_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescriptionl() {
		return description;
	}
	public void setDescription(String descriptionl) {
		this.description = descriptionl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getList_price() {
		return list_price;
	}
	public void setList_price(float list_price) {
		this.list_price = list_price;
	}
	public float getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(float current_price) {
		this.current_price = current_price;
	}
	public List<String> getRegions() {
		return regions;
	}
	public void setRegions(List<String> regions) {
		this.regions = regions;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public int getPurchase_count() {
		return purchase_count;
	}
	public void setPurchase_count(int purchase_count) {
		this.purchase_count = purchase_count;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public String getPurchase_deadline() {
		return purchase_deadline;
	}
	public void setPurchase_deadline(String purchase_deadline) {
		this.purchase_deadline = purchase_deadline;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getS_image_url() {
		return s_image_url;
	}
	public void setS_image_url(String s_image_url) {
		this.s_image_url = s_image_url;
	}
	public String getDeal_url() {
		return deal_url;
	}
	public void setDeal_url(String deal_url) {
		this.deal_url = deal_url;
	}
	public String getDeal_h5_url() {
		return deal_h5_url;
	}
	public void setDeal_h5_url(String deal_h5_url) {
		this.deal_h5_url = deal_h5_url;
	}
	public float getCommission_ratio() {
		return commission_ratio;
	}
	public void setCommission_ratio(float commission_ratio) {
		this.commission_ratio = commission_ratio;
	}
	public List<GroupBusinessEntity> getBusinesses() {
		return businesses;
	}
	public void setBusinesses(List<GroupBusinessEntity> businesses) {
		this.businesses = businesses;
	}
	
	
	
	
}
