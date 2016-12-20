package cn.cyansoft.contest.Yingxiang;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * 功能描述：网络请求统一出口 URL：http://loopj.com/android-async-http/
 * 
 * @date 2013-12-20下午6:04:46
 */
public class MyHttpClient extends AsyncHttpClient {
	
//	private CYLog log = CYLog.getInstance();
	
	private static final int TIME_OUT = 1000 * 10;// 网络请求连接超时时间
	private static final boolean ISDEBUG = true;
	private Context mContext;


	/**
	 * 
	 * 构造函数：
	 * 
	 * @autor xumengyang
	 */
	public MyHttpClient(Context context) {
		this.mContext = context;
		this.setTimeout(TIME_OUT);
		this.addHeader("Content-Type", "multipart/form-data");
	}

	/**
	 * 
	 * 功能描述：增加默认参数
	 * 
	 * @author 徐萌阳(xumengyang)
	 * @param @param params
	 * @return void
	 * @date 2013-12-22 下午2:36:38
	 * 
	 */
	private void addDefaultParams(RequestParams params) {
		// TODO
	}

	/**
	 * 
	 * 功能描述：发送Post请求
	 * 
	 * @author 严峥(yanzheng)
	 * @param @param url
	 * @param @param map
	 * @param @param handler
	 * @return void
	 * @date 2014-1-22 上午11:57:48
	 * 
	 *       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void post(String url, RequestParams params, MyAsyncResponseHandler handler) {
		super.post(mContext, url, params, handler);
	}

	/**
	 * 
	 * 功能描述：发送Get请求
	 * 
	 * @author 徐萌阳(xumengyang)
	 * @param @param url
	 * @param @param params
	 * @param @param handler
	 * @return void
	 * @date 2013-12-22 下午2:41:11
	 * 
	 */
	public void get(String url,String appKey,String appScrect, Map map, MyAsyncResponseHandler handler) {
		
		RequestParams params = new RequestParams();
		
		if (!StringUtils.isEmpty(url)) {
			StringBuffer sb = new StringBuffer();
			if (!StringUtils.isEmpty(map.toString())) {
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry pairs = (Entry) it.next();
					sb.append(pairs.getKey().toString() + "="
							+ pairs.getValue().toString() + "&");
					params.put(pairs.getKey().toString(), pairs.getValue());
				}
			}
			String sign = null;
			try {
				if(!StringUtils.isEmpty(sb.toString())){
					sign = sign(appKey, appScrect, map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.addHeader("sign", sign);
		}
		super.get(url, params, handler);
	}
	
	
	 /**
     * 获取请求字符串
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String getQueryString(String appKey, String secret, Map<String, String> paramMap)
    {
        String sign = sign(appKey, secret, paramMap);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet())
        {
            stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
        }
        String queryString = stringBuilder.toString();
        return queryString;
    }
	
    /**
     * 签名
     * 
     * @param appKey
     * @param secret
     * @param paramMap
     * @return
     */
    public static String sign(String appKey, String secret, Map<String, String> paramMap) {
        // 参数名排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接参数
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appKey);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }

        stringBuilder.append(secret);
        String codes = stringBuilder.toString();

        // SHA-1签名
        // For Android
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();

        return sign;
    }
	
}
