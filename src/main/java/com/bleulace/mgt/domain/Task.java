package com.bleulace.mgt.domain;

import java.util.List;
import java.util.Set;

import com.bleulace.crm.domain.Account;

public interface Task
{
	public String getTitle();

	public List<Comment> getComments();

	public void addComment(Comment comment);

	public void markAsCompleted();

	public void remove();

	public Set<Account> getAssignees();
}