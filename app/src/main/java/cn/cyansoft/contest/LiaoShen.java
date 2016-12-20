package cn.cyansoft.contest;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class LiaoShen extends BmobObject {
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserComment() {
        return UserComment;
    }

    public void setUserComment(String userComment) {
        UserComment = userComment;
    }

    private String UserName;
    private String UserComment;

}
