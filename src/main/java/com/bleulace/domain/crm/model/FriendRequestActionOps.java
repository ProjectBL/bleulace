package com.bleulace.domain.crm.model;

import org.axonframework.domain.MetaData;

interface FriendRequestActionOps
{
	public void execute(Account target, MetaData metaData);
}