package cn.cyansoft.contest.QQ;

import java.io.Serializable;
import java.util.List;

public class ImpressionEntity implements Serializable {
	private String id;				//主键唯一标识
	private String uid;				//第三方用户id
	private String username;		//第三方用户昵称
	private String uploadDate;		//照片上传时间
	private String picurl;			//图片URL
	private String loc;				//用户位置
	private String content;			//照片描述
	private String header_pic_url;	//用户头像URL
	private int likes;				//喜欢数
	private String liked_by;
	
	public String getLiked_by() {
		return liked_by;
	}
	public void setLiked_by(String liked_by) {
		this.liked_by = liked_by;
	}
	private List<CommentEntity> comment_list;//评论列表
	
	public List<CommentEntity> getComment_list() {
		return comment_list;
	}
	public void setComment_list(List<CommentEntity> comment_list) {
		this.comment_list = comment_list;
	}
	public String getHeader_pic_url() {
		return header_pic_url;
	}
	public void setHeader_pic_url(String header_pic_url) {
		this.header_pic_url = header_pic_url;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	


}
