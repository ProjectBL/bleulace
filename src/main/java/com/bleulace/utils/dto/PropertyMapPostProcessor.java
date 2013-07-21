package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
public class PropertyMapPostProcessor implements BeanPostProcessor
{
	@Autowired
	private ModelMapper mapper;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException
	{
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException
	{
		if (bean instanceof PropertyMap<?, ?>)
		{
			mapper.addMappings((PropertyMap<?, ?>) bean);
		}
		return bean;
	}

}
