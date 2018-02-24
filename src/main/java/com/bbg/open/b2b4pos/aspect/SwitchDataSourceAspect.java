package com.bbg.open.b2b4pos.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bbg.open.b2b4pos.annotation.SwitchDataSource;
import com.bbg.open.b2b4pos.common.DbcontextHolder;


@Aspect
@Component
@Order(0)
public class SwitchDataSourceAspect {
	
	@Pointcut("@within(com.bbg.open.b2b4pos.annotation.SwitchDataSource)")
    public  void serviceAspect() {}    
	
	
	 @Before("serviceAspect()")    
     public  void doBefore(JoinPoint joinPoint) {  
			String value = getAnnotationValue(joinPoint);
			DbcontextHolder.setDbType(value);
	 }
	 
	 
	 
	 
	 private  String getAnnotationValue(JoinPoint joinPoint)  {  
		return  joinPoint.getTarget().getClass().getAnnotation(SwitchDataSource.class).value();
	 }  

}
