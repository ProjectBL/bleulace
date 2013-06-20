package com.bleulace.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.util.ClassUtils;

public class ComponentClassScanner extends
		ClassPathScanningCandidateComponentProvider
{

	public ComponentClassScanner()
	{
		super(false);
	}

	public final Collection<Class<?>> getComponentClasses(String basePackage)
	{
		basePackage = basePackage == null ? "" : basePackage;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (BeanDefinition candidate : findCandidateComponents(basePackage))
		{
			try
			{
				Class<?> cls = ClassUtils.resolveClassName(
						candidate.getBeanClassName(),
						ClassUtils.getDefaultClassLoader());
				classes.add(cls);
			}
			catch (Throwable ex)
			{
				ex.printStackTrace();
			}
		}
		return classes;
	}

}