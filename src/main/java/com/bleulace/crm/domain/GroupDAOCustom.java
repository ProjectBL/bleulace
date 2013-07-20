package com.bleulace.crm.domain;

import java.util.List;

interface GroupDAOCustom
{
	public AccountGroup findOneByTitle(String title);

	public List<AccountGroup> findByTitle(String title);
}