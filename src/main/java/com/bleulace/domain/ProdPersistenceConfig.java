package com.bleulace.domain;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bleulace.PersistenceProfile;

@Profile("prod")
@Configuration
public class ProdPersistenceConfig implements PersistenceProfile
{
	@Override
	public DataSource dataSource()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> jpaPropsMap()
	{
		// TODO Auto-generated method stub
		return null;
	}
}