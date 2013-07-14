package com.bleulace.mgt.domain;

import java.util.List;

public interface Commentable
{
	public List<Comment> getComments();

	public void addComment(Comment comment);
}
