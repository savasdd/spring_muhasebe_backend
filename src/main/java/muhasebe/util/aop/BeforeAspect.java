package muhasebe.util.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
@Component
public class BeforeAspect {

	@Before("execution(* muhasebe.custom.impl.MuhKodServiceImpl.getKodById(..))")
	public void getKodById() {
		System.err.println("Executing Before on getKodById()");
	}

	@Before("execution(* muhasebe.custom.impl.MuhKodServiceImpl.getKodById(..)) && args(name)")
	public void getKodById(String name) {
		System.out.println("String argument passed=" + name);
	}
}
