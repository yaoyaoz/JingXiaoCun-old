package utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

public class WebUtils {

	public static <T> T request2Bean(HttpServletRequest request,
			Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String name = e.nextElement();
				String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
			
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
			
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
			
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
			
		}
		
	}

	public static String makeId() {
		//UUID	128	36位字符
		return UUID.randomUUID().toString();
	}
}
