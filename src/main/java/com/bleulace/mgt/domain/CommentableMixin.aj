package com.bleulace.mgt.domain;

import java.util.List;

interface CommentableMixin extends Commentable
{
	static aspect Impl
	{
		private List<Comment> Commentable.comments;
		
		public List<Comment> Commentable.getComments()
		{
			return comments;
		}
		
		public void Commentable.addComment(Comment comment)
		{
			this.comments.add(comment);
		}
	}
}
