package muhasebe.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class AfterAspect {

	@After("execution(* muhasebe.custom.impl.MuhKodServiceImpl.getKodById(..)) && args(name)")
	public void getKodById(String name) {
		System.out.println("Running After Advice and String argument passed=" + name);
	}

	@AfterThrowing("execution(* muhasebe.custom.impl.MuhKodServiceImpl.getKodById(..))")
	public void exception(JoinPoint joinPoint) {
		System.out.println("Exception thrown in Employee Method=" + joinPoint.toString());
	}

	@AfterReturning(pointcut = "execution(* muhasebe.custom.impl.MuhKodServiceImpl.getKodById(..))", returning = "returnString")
	public void getNameReturningAdvice(String returnString) {
		System.out.println("getNameReturningAdvice executed. Returned String=" + returnString);
	}
}
