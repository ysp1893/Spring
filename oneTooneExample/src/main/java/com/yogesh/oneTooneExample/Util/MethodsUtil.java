package com.yogesh.oneTooneExample.Util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

public class MethodsUtil {

	@SafeVarargs
	public static <T> boolean isNullOrEmpty(T... t) {
		for (T ob : t) {
			if (ob == null || ob.toString().trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static String getApiPathFromHttpServletRequest(HttpServletRequest httpServletRequest) {
		return httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
	}
}
