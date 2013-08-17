package com.bleulace.utils.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
class ModelMappingConfig
{
	@Autowired
	private ApplicationContext ctx;

	static final String DEFAULT = "defaultMapper";

	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	@Bean(name = DEFAULT)
	public ModelMapper modelMapper()
	{
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMethodAccessLevel(AccessLevel.PRIVATE);
		for (PropertyMap<?, ?> map : ctx.getBeansOfType(PropertyMap.class)
				.values())
		{
			mapper.addMappings(map);
		}
		return mapper;
	}
}