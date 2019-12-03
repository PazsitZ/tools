package hu.pazsitz.tools.java.utils;

public class StringUtils {

	public static boolean isBlank(final String str) {
		if (isEmpty(str)) {
			return true;
		}
		
		for (char chr : str.toCharArray()) {
			if (!Character.isWhitespace(chr)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}
	
	public static boolean isEmpty(final String str) {
		return str == null || str.length() == 0;
	}
	
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}
	
}
