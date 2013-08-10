package com.bleulace.domain.crm.model;


interface FriendRequestActionOps
{
	void execute(Account initiator, Account recipient);
}