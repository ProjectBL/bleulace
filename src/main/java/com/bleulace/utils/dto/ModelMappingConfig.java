package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMappingConfig implements ApplicationContextAware
{
	private ApplicationContext ctx;

	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper mapper = new ModelMapper();

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		for (PropertyMap<?, ?> map : ctx.getBeansOfType(PropertyMap.class)
				.values())
		{
			mapper.addMappings(map);
		}
		return mapper;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException
	{
		this.ctx = ctx;
	}
}