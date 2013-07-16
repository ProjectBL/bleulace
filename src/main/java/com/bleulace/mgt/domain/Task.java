package com.bleulace.mgt.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.axonframework.domain.IdentifierFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.Assert;

import com.bleulace.persistence.EventSourcedEntityMixin;

@Entity
@RooJavaBean
public class Task implements EventSourcedEntityMixin, CommentableMixin,
		AssignableMixin, Serializable
{
	private static final long serialVersionUID = 6010485686197407357L;

	@Id
	private String id = IdentifierFactory.getInstance().generateIdentifier();

	@ManyToOne
	@JoinColumn(updatable = false, nullable = false)
	private Project project;

	@Column(nullable = false)
	private String title;

	@SuppressWarnings("unused")
	private Task()
	{
	}

	Task(Project project, String title)
	{
		Assert.noNullElements(new Object[] { project, title });
		this.title = title;
		this.project = project;
		registerAggregateRoot(project);
	}
}