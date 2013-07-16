package com.bleulace.mgt.domain;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.application.command.CreateProjectCommand;
import com.bleulace.mgt.domain.event.ManagerAddedEvent;
import com.bleulace.mgt.domain.event.ProjectCreatedEvent;
import com.bleulace.persistence.EventSourcedAggregateRootMixin;

//@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@RooJavaBean
public abstract class AbstractProject implements EventSourcedAggregateRootMixin
{
	private static final long serialVersionUID = -1911715243742088159L;

	private String title;

	public AbstractProject(CreateProjectCommand command)
	{
		setId(command.getId());
		apply(command, ProjectCreatedEvent.class);
	}

	public void on(ProjectCreatedEvent event)
	{
		map(event);

		if (event.getCreatorId() != null)
		{
			apply(new ManagerAddedEvent(event.getCreatorId(),
					ManagementLevel.OWN));
		}
	}
}
