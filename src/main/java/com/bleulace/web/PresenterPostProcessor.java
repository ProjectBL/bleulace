package com.bleulace.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Arleigh Dickerson
 * 
 */
@Component
class PresenterPostProcessor implements BeanPostProcessor
{
	@Autowired
	private ApplicationContext ctx;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException
	{
		if (AnnotationUtils.findAnnotation(bean.getClass(), Presenter.class) != null)
		{
			ctx.getBean(PresenterSubscribingListener.class).registerPresenter(
					bean);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException
	{
		return bean;
	}

}
