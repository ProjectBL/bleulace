package com.bleulace.domain.resource.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.ManagementAssignment;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.resource.model.CompositeResource;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.web.annotation.WebProfile;

@Service
@WebProfile
class ManagementServiceImpl implements ManagementService
{
	@Autowired
	private AccountDAO accountDAO;

	@Override
	public void assignOwner(CompositeResource resource)
	{
		String currentId = SpringApplicationContext.getUser().getId();
		if (currentId == null)
		{
			return;
		}

		Account account = accountDAO.findOne(currentId);
		Assert.notNull(account);
		resource.addChild(new ManagementAssignment(account, ManagementLevel.OWN));
	}
}