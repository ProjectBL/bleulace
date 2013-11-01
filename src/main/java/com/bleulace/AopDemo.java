package com.bleulace;
import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.ui.Calendar;

public class AopDemo
{
	public static void foo()
	{
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* *.*(..))");
		pointcut.setBeanFactory(SpringApplicationContext.get());

		Advice advice = new AfterReturningAdvice()
		{
			@Override
			public void afterReturning(Object returnValue, Method method,
					Object[] args, Object target) throws Throwable
			{
				System.out.println(method);
			}
		};

		Advisor advisor = new DefaultPointcutAdvisor(pointcut, advice);

		Calendar calendar = new Calendar();
		ProxyFactory pf = new ProxyFactory(calendar);
		pf.addAdvisor(advisor);
		pf.setProxyTargetClass(true);
		Calendar c = (Calendar) pf.getProxy();
		c.setStartDate(new Date());
		c.setEndDate(new Date());
	}

	public static void bar()
	{
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(java.lang.Integer *.*(..))");
		pointcut.setBeanFactory(SpringApplicationContext.get());

		MethodInterceptor interceptor = new MethodInterceptor()
		{
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable
			{
				return new Integer(2);
			}
		};

		Advisor advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
		Baz b = new Baz();
		System.out.println(b.doIt());

		ProxyFactory pf = new ProxyFactory(b);
		pf.addAdvisor(advisor);
		pf.setProxyTargetClass(true);
		b = (Baz) pf.getProxy();
		System.out.println(b.doIt());
	}

	public static class Baz
	{
		public Integer doIt()
		{
			return 1;
		}
	}

}
