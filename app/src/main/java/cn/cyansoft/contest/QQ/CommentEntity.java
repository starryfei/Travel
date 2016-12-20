package cn.cyansoft.contest.QQ;

import java.io.Serializable;

public class CommentEntity implements Serializable {

	private String id;
	private String name;
	private String header_url;
	private String comment;
	private String date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHeader_url() {
		return header_url;
	}
	public void setHeader_url(String header_url) {
		this.header_url = header_url;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
