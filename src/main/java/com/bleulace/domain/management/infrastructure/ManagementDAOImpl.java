package com.bleulace.domain.management.infrastructure;

import org.springframework.util.Assert;

import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.QManagementAssignment;
import com.bleulace.jpa.config.QueryFactory;

class ManagementAssignmentDAOImpl implements ManagementAssignmentDAOCustom
{
	@Override
	public ManagementAssignment findAssignment(String resourceId,
			String accountId)
	{
		Assert.notNull(accountId);
		if (resourceId == null)
		{
			return null;
		}
		QManagementAssignment a = new QManagementAssignment("a");
		return QueryFactory
				.from(a)
				.where(a.resource.id.eq(resourceId).and(
						a.account.id.eq(accountId))).uniqueResult(a);
	}
}