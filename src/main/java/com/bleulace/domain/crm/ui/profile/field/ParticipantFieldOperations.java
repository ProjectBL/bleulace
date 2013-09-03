package com.bleulace.domain.crm.ui.profile.field;

import com.bleulace.domain.management.model.ManagementLevel;

interface ParticipantFieldOperations
{
	public void guestInvited(String id);

	public void guestRemoved(String id);

	public void managerAdded(String id, ManagementLevel level);

	public void managerRemoved(String id);
}