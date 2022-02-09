package muhasebe.util.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import muhasebe.custom.Service;
import muhasebe.model.MuhIslemLog;
import muhasebe.util.AuthUtil;
import muhasebe.util.HttpUtil;
import muhasebe.util.JacksonUtil;

@Aspect
@Component
public class ElasticLogAspect {

	@Autowired
	private JacksonUtil json;

	@Autowired
	public Service service;

	@Autowired
	public AuthUtil util;

	@Pointcut("@annotation(muhasebe.util.aop.ElasticLog)")
	public void logAnnotation() {

	}

	@Around(value = "logAnnotation() && @annotation(elasticLog)")
	public Object createIslemLog(ProceedingJoinPoint joinPoint, ElasticLog elasticLog) throws Throwable {
		Object result = null;
		MuhIslemLog dto = new MuhIslemLog();

		Class<? extends Object> sinif = joinPoint.getTarget().getClass();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method metot = signature.getMethod();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		dto.setServis(sinif.getName() + " => " + metot.getName());
		dto.setMetot(HttpUtil.getMethod(request));
		dto.setPath(HttpUtil.getPath(request));
		dto.setCreateDate(new Date());
		dto.setKullaniciAdi(util.getUser().getKullaniciAdi());
		// log.info(HttpUtil.getHeaderMap(request, false));

		///// Before Method Execution /////
		result = joinPoint.proceed();
		///// After Method Execution /////

		ResponseEntity<?> response = (ResponseEntity<?>) result;
		if (ObjectUtils.isNotEmpty(response)) {
			Object body = response.getBody();
			dto.setStatus((long) response.getStatusCodeValue()); // status
			dto.setBody(body); // body
			// log.info(json.convertObjectToJson(body));
		}

		service.getLog().createIslem(dto);
		return result;
	}

}
