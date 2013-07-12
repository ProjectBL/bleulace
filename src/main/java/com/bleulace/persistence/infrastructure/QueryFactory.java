package com.bleulace.persistence.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Configurable;

import com.mysema.query.jpa.EclipseLinkTemplates;
import com.mysema.query.jpa.JPQLTemplates;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;

@Configurable(preConstruction = true)
public class QueryFactory
{
	private static final JPQLTemplates TEMPLATES = EclipseLinkTemplates.DEFAULT;

	@PersistenceContext
	private EntityManager entityManager;

	private QueryFactory()
	{
	}

	public static JPAQuery from(EntityPath<?>... args)
	{
		return make().from(args);
	}

	private static JPAQuery make()
	{
		return new JPAQuery(new QueryFactory().entityManager, TEMPLATES);
	}
}