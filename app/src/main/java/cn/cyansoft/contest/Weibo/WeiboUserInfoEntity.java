package cn.cyansoft.contest.Weibo;

import java.io.Serializable;

public class WeiboUserInfoEntity implements Serializable {
	private long id;					//微博Id
	private String name;				//微博昵称
	private String location;			//位置
	private String description;			//个性签名
	private String profile_image_url;	//头像
	private String followers_count;		//粉丝数
	private String friends_count;		//关注数
	private String statuses_count;		//微博数
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getProfile_image_url() {
		return profile_image_url;
	}
	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(String friends_count) {
		this.friends_count = friends_count;
	}
	public String getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(String statuses_count) {
		this.statuses_count = statuses_count;
	}
	
	

	/**
	 * {
		    "id": 1998418174,
		    "idstr": "1998418174",
		    "class": 1,
		    "screen_name": "海鴎Beyond",
		    "name": "海鴎Beyond",
		    "province": "11",
		    "city": "2",
		    "location": "北京 西城区",
		    "description": "一切美好，只因梦在心中，路在脚下",
		    "url": "",
		    "profile_image_url": "http://tp3.sinaimg.cn/1998418174/50/5647432354/1",
		    "cover_image": "http://ww4.sinaimg.cn/crop.0.1.980.300/771d70fejw1e3lvh4jtcfj.jpg",
		    "cover_image_phone": "http://ww1.sinaimg.cn/crop.0.0.640.640/7c85468fjw1e8yqchu1r6j20hs0hs40q.jpg",
		    "profile_url": "oyhb1738",
		    "domain": "oyhb1738",
		    "weihao": "",
		    "gender": "m",
		    "followers_count": 1553,
		    "friends_count": 919,
		    "statuses_count": 3236,
		    "favourites_count": 607,
		    "created_at": "Sat Mar 12 19:57:23 +0800 2011",
		    "following": false,
		    "allow_all_act_msg": false,
		    "geo_enabled": true,
		    "verified": false,
		    "verified_type": 220,
		    "remark": "",
		    "status": {
		        "created_at": "Fri May 02 12:15:39 +0800 2014",
		        "id": 3705754311504048,
		        "mid": "3705754311504048",
		        "idstr": "3705754311504048",
		        "text": "应该是考上了北京建筑工程学院吧",
		        "source": "<a href=\"http://weibo.com/\" rel=\"nofollow\">新浪微博</a>",
		        "favorited": false,
		        "truncated": false,
		        "in_reply_to_status_id": "",
		        "in_reply_to_user_id": "",
		        "in_reply_to_screen_name": "",
		        "pic_urls": [
		            
		        ],
		        "geo": null,
		        "reposts_count": 0,
		        "comments_count": 0,
		        "attitudes_count": 0,
		        "mlevel": 0,
		        "visible": {
		            "type": 0,
		            "list_id": 0
		        }
		    },
		    "ptype": 0,
		    "allow_all_comment": true,
		    "avatar_large": "http://tp3.sinaimg.cn/1998418174/180/5647432354/1",
		    "avatar_hd": "http://tp3.sinaimg.cn/1998418174/180/5647432354/1",
		    "verified_reason": "",
		    "verified_trade": "",
		    "verified_reason_url": "",
		    "verified_source": "",
		    "verified_source_url": "",
		    "follow_me": false,
		    "online_status": 1,
		    "bi_followers_count": 718,
		    "lang": "zh-cn",
		    "star": 0,
		    "mbtype": 14,
		    "mbrank": 1,
		    "block_word": 0,
		    "block_app": 0
		}
	 */
	
	
	
}
