package com.bleulace.web.demo.calendar.appearance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.management.model.EventInvitee;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.web.SystemUser;

@Component
class SingleVieweeStrategy implements StyleNameCallback
{
	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private SystemUser user;

	@Override
	public String evaluate(PersistentEvent source)
	{
		for (String id : user.getTargetIds())
		{
			Account account = accountDAO.findOne(id);
			if (account == null)
			{
				continue;
			}

			EventInvitee invitee = source.getInvitees().get(account);
			if (invitee == null)
			{
				continue;
			}

			RsvpStatus status = invitee.getStatus();
			return status == null ? null : status.getStyleName();
		}
		return null;
	}
}