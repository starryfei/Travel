package cn.cyansoft.contest.QQ;

import java.io.Serializable;

public class QQUserInfoEntity implements Serializable {

	private String nickname;	//用户在QQ空间的昵称
	private String figureurl_1;	//大小为50×50像素的QQ空间头像URL
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFigureurl_1() {
		return figureurl_1;
	}
	public void setFigureurl_1(String figureurl_1) {
		this.figureurl_1 = figureurl_1;
	}
	
}
