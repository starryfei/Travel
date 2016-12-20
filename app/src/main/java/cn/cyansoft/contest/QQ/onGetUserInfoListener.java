package cn.cyansoft.contest.QQ;

import cn.cyansoft.contest.Weibo.WeiboUserInfoEntity;

public interface onGetUserInfoListener {

	public void getWeiboUserInfo(WeiboUserInfoEntity weiboUserInfo);
	public void getQQUserInfo(QQUserInfoEntity qqUserInfo);
}
