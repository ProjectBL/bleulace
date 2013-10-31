package com.bleulace.jpa;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.SystemUser;

class ResourceDAOBase<T extends AbstractResource> extends
		SimpleJpaRepository<T, String>
{
	public ResourceDAOBase(Class<T> domainClass, EntityManager em)
	{
		super(domainClass, em);
	}

	@Override
	public <S extends T> S save(S entity)
	{
		SystemUser user = SpringApplicationContext.getBean(SystemUser.class);
		if (user.getId() != null && entity.isNew()
				&& entity.getManagerIds(ManagementLevel.OWN).isEmpty())
		{
			entity.setManagementLevel(user.getId(), ManagementLevel.OWN);
		}
		return super.save(entity);
	}
}
