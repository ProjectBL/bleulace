package com.bleulace.mgt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.bleulace.crm.domain.Account;

public interface CommentMixin extends Comment
{
	static aspect Impl
	{
		@Column(nullable = false)
		private String CommentMixin.content;

		@ManyToOne
		@JoinColumn(updatable = false, nullable = false)
		private Account CommentMixin.author;

		@Temporal(TemporalType.TIMESTAMP)
		private Date CommentMixin.datePosted;

		public Account CommentMixin.getAuthor()
		{
			return author;
		}
		
		private void CommentMixin.setAuthor(Account author)
		{
			this.author = author;
		}

		public String CommentMixin.getContent()
		{
			return content;
		}
		
		private void CommentMixin.setContent(String content)
		{
			this.content = content;
		}

		public DateTime CommentMixin.getDatePosted()
		{
			return new DateTime(datePosted);
		}
	}
}