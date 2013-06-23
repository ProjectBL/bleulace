package com.bleulace.domain;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

public interface PersistenceProfile
{
	public DataSource dataSource();

	@Bean(name = "jpaPropsMap")
	public Map<String, String> jpaPropsMap();
}
