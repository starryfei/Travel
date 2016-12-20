package cn.cyansoft.contest.cn.cyansoft.dazhongdianping;

/**
 * 
 * 功能描述: String判断类
 * @author 欧阳海冰(OuyangHaibing)
 * @date 2014-3-28 下午5:17:02
 */
public class StringUtils {

	/**
	 * 
	 * 功能描述: 非空判断
	 * @param @param str
	 * @param @return  
	 * @return boolean    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:17:18
	 */
	public static boolean isEmpty(String str) {
		if (null != str && !"".equals(str)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 功能描述: 判断字符串是否由数字组成
	 * @param @param str
	 * @param @return  
	 * @return boolean    返回类型 
	 * @author 欧阳海冰(OuyangHaibing)
	 * @date 2014-3-28 下午5:17:33
	 */
	public static boolean isNumeric(String str){
	    for (int i = str.length();--i>=0;){
	    	if (!Character.isDigit(str.charAt(i))){
	    		return false;
	    	}
	    }
	    return true;
	}
	
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}

}
