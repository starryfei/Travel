package cn.cyansoft.contest;

public class Constants {

	//锦州的经纬坐标
	public static final String LATITUDE = "41.149149";
	public static final String LONGITUDE = "121.130437";


	//登录方式
	public static final String LOGIN_MODE = "login_mode";
	public static final String LOGINMODE_QQ = "qq";
	public static final String LOGINMODE_SINA = "sina";

	public static class QQParams{
		/**QQ第三方登陆需要自己的 APP_ID*/
		public static final String APP_ID = "1105237212";
		/**应用需要用户增量授权权限,由“,”分隔。例如：“get_user_info,add_topic”,全部可写为all*/
		public static final String SCOPE = "all";
		/**QQ登陆返回的用户标识,用于唯一标识用户身份（每一个openid与QQ号码对应）。*/
		public static final String QQOPEN_ID = "openid";
		/**用户进行应用邀请、分享、支付等基本业务请求的凭据。*/
		public static final String QQACCESS_TOKEN = "access_token";
		public static final String QQEXPIRES_IN = "expires_in";

		public static final String GRAPH_SIMPLE_USER_INFO = "user/get_simple_userinfo";
		public static final String GET = "GET";
		public static final String GRAPH_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";
		public static final String GRAPH_USER_OPENID_URL = "https://graph.qq.com/oauth2.0/me";

		public static final String QQ_HEAD_URL = "qq_head_url";
		public static final String QQ_NICK_NAME = "qq_nick_name";
		public static final String Name = "★starry、";

	}

	public static class WeiboParams{
		/** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
		public static final String WEIBO_APP_KEY = "68811792";

		/**
		 * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
		 */
		public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

		/**
		 * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
		 * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利 选择赋予应用的功能。
		 */
		public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
				+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
				+ "follow_app_official_microblog," + "invitation_write";

		/** 注销地址（URL） */
		public static final String REVOKE_OAUTH_URL = "https://api.weibo.com/oauth2/revokeoauth2";

		/** 通过 code 获取 Token 的 URL */
		public static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
		/** 微博分享URL */
		public static final String PUBLISH_ONE_WEIBO_URL = "https://upload.api.weibo.com/2/statuses/upload.json";

		public static final String WEIBO_HEAD_URL = "weibo_head_url";
		public static final String WEIBO_NICK_NAME = "weibo_nick_name";
		public static final String WEIBO_DESCRIPTION = "weibo_description";
		public static final String WEIBO_ACCESS_TOKEN = "weibo_access_token";
		public static final String WEIBO_EXPIRES_IN = "weibo_expires_in";
		public static final String WEIBO_UID = "weibo_uid";

	}

	//百度地图APIKEY
	public static final String BAIDU_MAP_KEY = "0CL2HCFE7BWk4az2miHsLOEt";



	//大众点评
	public static class DZDianpingParams{
		public static final String BUSINESS_API_URL = "http://api.dianping.com/v1/business/find_businesses";
		public static final String GROUPON_API_URL = "http://api.dianping.com/v1/deal/find_deals";
		public static final String DP_APP_KEY = "669010072";
		public static final String DP_APP_SECRET = "f27205e7af9c44a790cc5480ce66bc5e";

	}

	//ImageLoader加载线程池的线程数量
	public static final int IMAGE_LOADER_THREAD_COUNT = 3;
	//加载网络图片的连接超时时间
	public static final int LOAD_IMAGE_CONNECT_TIMEOUT = 10 * 1000;
	//加载网络图片的读取超时时间
	public static final int LOAD_IMAGE_READ_TIMEOUT = 10 * 1000;

	public static class FragmentTag {
		//攻略
		public static final String GUIDE_FRAGMENT_TAG = "GuideFragment";
		//地图
		public static final String MAP_FRAGMENT_TAG = "TuCaoFragment";
		//印象
		public static final String IMPRESS_FRAGMENT_TAG = "ImpressFragment";
		//更多
		public static final String MORE_FRAGMENT_TAG = "MoreFragment";
		//团购
		public static final String GROUPON_FRAGMENT_TAG = "GrouponListFragment";
		//商户详情页
		public static final String BUSINESS_DETAIL_FRAGMENT_TAG = "BusinessDetailFragment";
		//登录
		public static final String LOGIN_FRAGMENT_TAG = "LoginFragment";

		public static final String SAI_EN = "SN";
		public static final String DIAN_SHI_TAI="DST";


	}
	//微博、QQ登录的返回码
	public static final int WEIBORESULTCODE = 11;
	public static final int QQRESULTCODE = 12;

	public static final String IP = "http://123.56.90.17";

	public static class ServerParams{
		//服务端上传印象URL
		public static final String PUBLISH_ONE_IMPRESSION_URL = IP+"/impress/upload.json";
		//获取所有印象URL
		public static final String GET_ALL_IMPRESSIONS_URL = IP+"/impress/getAllImpressions.json";
		//获取我的印象URL
		public static final String GET_MY_IMPRESSIONS_URL = IP+"/impress/getMyImpressionByUid.json";
		//为印象添加一条评论URL
		public static final String ADD_COMMENT_URL = IP+"/impress/addAComment.json";
		//获取某条印象下的所有评论URL
		public static final String GET_COMMENT_BY_IMPRESSIONID_URL = IP+"/impress/getCommentsByImpressionId.json";
		//修改like URL
		public static final String UPDATE_LIKES_URL = IP+"/impress/addLike.json";
		//获取likes URL
		public static final String GET_LIKES_URL = IP+"/impress/getLikes.json";
		//获取我的likes URL
		public static final String GET_MY_LIKES_URL = IP+"/impress/getMyLikes.json";
		//增加一条反馈 URL
		public static final String ADD_FEEDBACK_URL = IP+"/impress/addFeedback.json";
	}

	public static class ScenerySpotParams{
		public static final String GUOJISHENGTAIGUAN_1 = IP+"/celebrity/gjstg1.jpg";
		public static final String GUOJISHENGTAIGUAN_2 = IP+"/celebrity/gjstg2.jpg";

		public static final String SHUIYUNZHIWUJUCHANG_1 = IP+"/celebrity/syzwjc1.jpg";
		public static final String SHUIYUNZHIWUJUCHANG_2 = IP+"/celebrity/syzwjc2.jpg";

		public static final String BAIHUAGUAN_1 = IP+"/celebrity/bhg1.jpg";
		public static final String BAIHUAGUAN_2 = IP+"/celebrity/bhg2.jpg";

		public static final String BAIHUATA_1 = IP+"/celebrity/bht1.jpg";
		public static final String BAIHUATA_2 = IP+"/celebrity/bht2.jpg";

		public static final String HAIYANGKEJICHUANGYIGUAN_1 = IP+"/celebrity/hykjcyg1.jpg";
		public static final String HAIYANGKEJICHUANGYIGUAN_2 = IP+"/celebrity/hykjcyg2.jpg";

		public static final String MIGONGYOUYUAN_1 = IP+"/celebrity/mgyy1.jpg";
		public static final String MIGONGYOUYUAN_2 = IP+"/celebrity/mgyy2.jpg";

		public static final String YANYIGUANGCHANG_1 = IP+"/celebrity/yygc1.jpg";
		public static final String YANYIGUANGCHANG_2 = IP+"/celebrity/yygc2.jpg";

		public static final String HAIWANHUATIAN_1 = IP+"/celebrity/hwht1.jpg";
		public static final String HAIWANHUATIAN_2 = IP+"/celebrity/hwht2.jpg";

		public static final String MUDANSHAOYAOYUAN_1 = IP+"/celebrity/mdsyy1.jpg";
		public static final String MUDANSHAOYAOYUAN_2 = IP+"/celebrity/mdsyy2.jpg";

		public static final String BAICAOYUAN_1 = IP+"/celebrity/bcy1.jpg";
		public static final String BAICAOYUAN_2 = IP+"/celebrity/bcy2.jpg";

		public static final String XINGHUAYUAN_1 = IP+"/celebrity/xhy1.jpg";
		public static final String XINGHUAYUAN_2 = IP+"/celebrity/xhy2.jpg";

		public static final String ZHANGHEGUOJISHUHUASHIYUAN = IP+"/celebrity/zhgjshsy.jpg";

		public static final String HELAN_1 = IP+"/scenery/hl1.jpg";
		public static final String HELAN_2 = IP+"/scenery/hld1.jpg";

		public static final String PUTAOYA_1 = IP+"/scenery/pty1.jpg";
		public static final String PUTAOYA_2 = IP+"/scenery/pty2.jpg";

		public static final String GELUNBIYA_1 = IP+"/scenery/glby1.jpg";
		public static final String GELUNBIYA_2 = IP+"/scenery/glby2.jpg";

		public static final String XIBANYA_1 = IP+"/scenery/xby1.jpg";
		public static final String XIBANYA_2 = IP+"/scenery/xby2.jpg";

		public static final String XINXILAN_1 = IP+"/scenery/xxl1.jpg";
		public static final String XINXILAN_2 = IP+"/scenery/xxl2.jpg";

		public static final String YILANG_1 = IP+"/scenery/yl1.jpg";
		public static final String YILANG_2 = IP+"/scenery/yl2.jpg";

		public static final String MOXIGE_1 = IP+"/scenery/mxg1.jpg";
		public static final String MOXIGE_2 = IP+"/scenery/mxg2.jpg";

		public static final String ANSHAN_1 = IP+"/scenery/ans1.jpg";
		public static final String ANSHAN_2 = IP+"/scenery/ans2.jpg";

		public static final String JINZHOU_1 = IP+"/scenery/jz1.jpg";
		public static final String JINZHOU_2 = IP+"/scenery/jz2.jpg";

		public static final String BENXI_1 = IP+"/scenery/bx1.jpg";
		public static final String BENXI_2 = IP+"/scenery/bx2.jpg";

		public static final String FUXIN_1 = IP+"/scenery/fx1.jpg";
		public static final String FUXIN_2 = IP+"/scenery/fx2.jpg";

		public static final String TIELING_1 = IP+"/scenery/tl1.jpg";
		public static final String TIELING_2 = IP+"/scenery/tl2.jpg";

		public static final String HULUDAO_1 = IP+"/scenery/hld1.jpg";
		public static final String HUKUDAO_2 = IP+"/scenery/hld2.jpg";

		public static final String PANJIN_1 = IP+"/scenery/pj1.jpg";
		public static final String PANJIN_2 = IP+"/scenery/pj2.jpg";

		public static final String YINGKOU_1 = IP+"/scenery/yk1.jpg";
		public static final String YINGKOU_2 = IP+"/scenery/yk2.jpg";

		public static final String DANDONG_1 = IP+"/scenery/dd1.jpg";
		public static final String DANDONG_2 = IP+"/scenery/dd2.jpg";

		public static final String DALIAN_1 = IP+"/scenery/dl1.jpg";
		public static final String DALIAN_2 = IP+"/scenery/dl2.jpg";

		public static final String SHENYANG_1 = IP+"/scenery/sy1.jpg";
		public static final String SHENYANG_2 = IP+"/scenery/sy2.jpg";

		public static final String FUSHUN_1 = IP+"/scenery/fs1.jpg";
		public static final String FUSHUN_2 = IP+"/scenery/fs2.jpg";

		public static final String CHAOYANG_1 = IP+"/scenery/cy1.jpg";
		public static final String CHAOYANG_2 = IP+"/scenery/cy2.jpg";

	}


	public static class LocParams{
		public static final double GJST_LATITUDE = 40.922647;//116.39583,39.941505
		public static final double GJST_LONGITUDE = 121.204802;

		public static final double SYZWJC_LATITUDE = 40.915369;//116.414756,39.924928
		public static final double SYZWJC_LONGITUDE = 121.199915;

		public static final double BHG_LATITUDE = 40.914909;//116.389918,39.950881
		public static final double BHG_LONGITUDE = 121.20133;

		public static final double BHT_LATITUDE = 40.909958;//116.3859,39.941657
		public static final double BHT_LONGITUDE = 121.200508;

		public static final double HYKJCYG_LATITUDE = 40.918109;//116.395772,39.945584
		public static final double HYKJCYG_LONGITUDE = 121.205036;

		public static final double MGYY_LATITUDE = 40.922225;//39.925091,116.393684
		public static final double MGYY_LONGITUDE = 121.208018;

		public static final double YYGC_LATITUDE = 40.920988;//39.935638,116.376193
		public static final double YYGC_LONGITUDE = 121.210358;

		public static final double HWHT_LATITUDE = 40.912725;//116.391337,39.941325
		public static final double HWHT_LONGITUDE = 121.200293;

		public static final double MDSYY_LATITUDE = 40.922167;//116.380321,39.953024
		public static final double MDSYY_LONGITUDE = 121.209384;

		public static final double BCY_LATITUDE = 40.9151;//39.942416,116.388062
		public static final double BCY_LONGITUDE = 121.200692;

		public static final double XHY_LATITUDE = 40.92124;//39.946595,116.385872
		public static final double XHY_LONGITUDE = 121.210444;

		public static final double ZHGJSHSY_LATITUDE = 40.921976;//39.944753,116.388962
		public static final double ZHGJSHSY_LONGITUDE = 121.202754;

		public static final double HL_LATITUDE = 40.919734;//116.390984,39.950397
		public static final double HL_LONGITUDE = 121.209111;

		public static final double PTY_LATITUDE = 40.921458;//116.393131,39.942362
		public static final double PTY_LONGITUDE = 121.207726;

		public static final double GLBY_LATITUDE = 40.915168;//116.392109,39.951918
		public static final double GLBY_LONGITUDE = 121.205885;

		public static final double XBY_LATITUDE = 40.915216;//116.396938,39.94732
		public static final double XBY_LONGITUDE = 121.206729;

		public static final double XXL_LATITUDE = 40.915114;//116.388034,39.941809
		public static final double XXL_LONGITUDE = 121.201878;

		public static final double FX_LATITUDE = 40.915182;//116.40008,39.945675
		public static final double FX_LONGITUDE = 121.204573;
	}

}