package muhasebe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

public class HttpUtil {

	public static String getBody(HttpServletRequest request) {
		String bodyStr = StringUtils.EMPTY;

		InputStream inputStream;
		try {
			inputStream = request.getInputStream();
			byte[] body = StreamUtils.copyToByteArray(inputStream);
			inputStream.close();
			bodyStr = new String(body);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bodyStr;
	}

	public static String getMethod(HttpServletRequest request) {
		return ObjectUtils.isNotEmpty(request) ? request.getMethod() : StringUtils.EMPTY;
	}

	public static String getPath(HttpServletRequest request) {
		return ObjectUtils.isNotEmpty(request) && StringUtils.isNotEmpty(request.getRequestURI())
				? request.getRequestURI().substring(request.getContextPath().length())
				: StringUtils.EMPTY;
	}

	public static Map<String, String> getHeaderMap(HttpServletRequest request, boolean skipAuthorizationHeader) {
		Map<String, String> headerMap = new HashMap<String, String>();
		if (ObjectUtils.isNotEmpty(request) && ObjectUtils.isNotEmpty(request.getHeaderNames())) {
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				if (skipAuthorizationHeader && key.equalsIgnoreCase("Authorization")) {
					continue;
				}
				String value = request.getHeader(key);
				headerMap.put(key, value);
			}
		}
		return headerMap;
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> parameterMap = new HashMap<String, String>();
		if (ObjectUtils.isNotEmpty(request) && ObjectUtils.isNotEmpty(request.getParameterNames())) {
			Enumeration parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String key = (String) parameterNames.nextElement();
				String value = request.getParameter(key);
				parameterMap.put(key, value);
			}
		}
		return parameterMap;
	}

	public static boolean isNotEmpty(final Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	public static boolean isEmpty(final Map<?, ?> map) {
		return !isNotEmpty(map);
	}

}