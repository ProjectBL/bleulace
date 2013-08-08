package com.bleulace.mgt.domain;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.OrderBy;

import com.bleulace.crm.domain.Account;
import com.bleulace.mgt.domain.event.CommentAddedEvent;
import com.bleulace.utils.jpa.EntityManagerReference;

public interface Commentable
{
	public String getId();

	public List<Comment> getComments();

	interface Mixin extends Commentable
	{
		static aspect Impl
		{
			@OrderBy("datePosted ASC")
			@ElementCollection
			private List<Comment> Mixin.comments;

			public List<Comment> Mixin.getComments()
			{
				return comments;
			}

			public void Mixin.on(CommentAddedEvent event)
			{
				if (this.getId().equals(event.getId()))
				{
					Account author = EntityManagerReference.get().getReference(
							Account.class, event.getAccountId());
					Comment comment = new Comment(author, event.getContent());
					this.comments.add(comment);
				}
			}
		}
	}
}