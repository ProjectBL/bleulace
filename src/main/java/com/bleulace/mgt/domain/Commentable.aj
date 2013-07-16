package com.bleulace.mgt.domain;

import java.util.List;

import javax.persistence.ElementCollection;

public interface Commentable
{
	public List<Comment> getComments();

	interface Mixin extends Commentable
	{
		static aspect Impl
		{
			@ElementCollection(targetClass = Comment.class)
			private List<Comment> Mixin.comments;

			public List<Comment> Mixin.getComments()
			{
				return comments;
			}
		}
	}
}
