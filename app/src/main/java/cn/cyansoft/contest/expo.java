package cn.cyansoft.contest;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
public class expo extends BmobObject {


    private String UserName;
    private String UserComment;



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
}
