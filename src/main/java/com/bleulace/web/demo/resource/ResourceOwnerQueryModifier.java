package com.bleulace.web.demo.resource;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.resource.infrastructure.ResourceDAO;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;

@Component
class ResourceOwnerQueryModifier extends DefaultQueryModifierDelegate
{
	@Autowired
	private ResourceDAO dao;

	@Override
	public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> query, List<Predicate> predicates)
	{
		final Root<?> r = query.getRoots().iterator().next();
		predicates.add(r.get("id")
				.in(dao.findIdsByManager(SpringApplicationContext.getUser()
						.getId())));
	}
}
