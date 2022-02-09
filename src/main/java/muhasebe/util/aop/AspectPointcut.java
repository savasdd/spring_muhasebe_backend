package muhasebe.util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class AspectPointcut {

	@Pointcut("execution(* muhasebe.custom.impl.MuhKodServiceImpl.*(..))")
	public void serviceMethodExecution() {
	}

	@Around(value = "serviceMethodExecution() && args(param1)")
	public void aroundServiceMethodExecution(final ProceedingJoinPoint pjp, final String param1) throws Throwable {

		System.err.println("Before thing: " + param1);
		pjp.proceed();
		System.err.println("After thing: " + param1);
	}

}
