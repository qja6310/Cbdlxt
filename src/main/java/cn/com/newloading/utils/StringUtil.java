package cn.com.newloading.utils;

public class StringUtil {

	/**
	 * 判断字符串是否为空,长度为0,默认为空,对空格不去除
	 * 例如String str = "  ";str.length() == 2,非空返回true
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		if(s == null || s.length() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为空,长度为0,默认为空,对空格不去除
	 * 例如String str = "  ";str.length() == 2,空返回true
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if(s == null || s.length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空,长度为0,默认为空,对空格去除
	 * 例如String str = "  ";str.length() == 0,非空返回true
	 * @param s
	 * @return
	 */
	public static boolean isNotBlank(String s) {
		if(s == null || s.trim().length() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为空,长度为0,默认为空,对空格去除
	 * 例如String str = "  ";str.length() == 0,空返回true
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		if(s == null || s.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
}
