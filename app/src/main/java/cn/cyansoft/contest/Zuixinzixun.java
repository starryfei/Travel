package cn.cyansoft.contest;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/4/12 0012.
 */
public class Zuixinzixun extends BmobObject {
    private String Title;
    private int ResouId;
    private String Context;
    private String T_content;
    private BmobFile Image;

    public BmobFile getImage() {
        return Image;
    }

    public String getT_content() {
        return T_content;
    }

    public String getTitle() {
        return Title;
    }

    public String getContext() {
        return Context;
    }

    public int getResouId() {
        return ResouId;
    }

    public void setResouId(int resouId) {
        ResouId = resouId;
    }
}
