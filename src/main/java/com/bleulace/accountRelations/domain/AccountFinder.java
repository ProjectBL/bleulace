package com.bleulace.accountRelations.domain;

public interface AccountFinder
{
	public Account findById(String id);

	public Account findByEmail(String email);
}