package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
	
	/**
	 * 功能描述：获取JacksonMapper
	 */
	public static ObjectMapper getMapper(){
		return new ObjectMapper().configure(
				DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
				true);
	}
	
	public static String initJsonArrayStr(String content) throws Exception {
		JSONArray array = new JSONArray(content);
		JSONObject obj = new JSONObject();
		obj.put("data", array);
		return obj.toString();
	}
}
