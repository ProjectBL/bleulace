package com.bleulace.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bleulace.utils.CallByName;
import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;

public class IdCollectionQueryModifier extends DefaultQueryModifierDelegate
{
	private final CallByName<Collection<String>> ids;

	public IdCollectionQueryModifier(CallByName<Collection<String>> ids)
	{
		this.ids = ids;
	}

	@Override
	public void filtersWillBeAdded(CriteriaBuilder criteriaBuilder,
			CriteriaQuery<?> query, List<Predicate> predicates)
	{
		Root<?> root = query.getRoots().iterator().next();
		predicates.add(root.get("id").in(ids.evaluate()));
	}
}
