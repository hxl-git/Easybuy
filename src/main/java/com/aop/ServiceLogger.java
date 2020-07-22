package com.aop;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ServiceLogger {
	private static final Logger log = Logger.getLogger(ServiceLogger.class);

	
	
//	@Pointcut("execution(* cn.hxl.dao..*.*(..))")
//	public void pointcut() {};
//	
//	
//	@Before("pointcut()")
//	public void before(JoinPoint jp) {
//	    log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
//	            + " 方法。方法入参：" + Arrays.toString(jp.getArgs()));
//	}
//	@AfterReturning(pointcut="pointcut()",returning="result")
//	public void afterReturning(JoinPoint jp, Object result) {
//	    log.info("调用 " + jp.getTarget() + " 的 " + jp.getSignature().getName()
//	            + " 方法。方法返回值：" + result);
//	}

}