package com.bleulace.web.demo.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bleulace.utils.CallByName;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;

public class ResourceQueryModifier extends DefaultQueryModifierDelegate
{
	private CallByName<Collection<String>> managerIds;

	@Override
	public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> query, List<Predicate> predicates)
	{
		if (managerIds != null)
		{
			Root<?> root = query.getRoots().iterator().next();
			predicates.add(root.joinList("assignments").get("account")
					.get("id").in(managerIds.evaluate()));
		}
	}

	public void setManagerIds(CallByName<Collection<String>> managerIds)
	{
		this.managerIds = managerIds;
	}

	public void setManagerId(final CallByName<String> managerId)
	{
		managerIds = new CallByName<Collection<String>>()
		{
			@Override
			public Collection<String> evaluate()
			{
				return Collections.singleton(managerId.evaluate());
			}
		};
	}
}
