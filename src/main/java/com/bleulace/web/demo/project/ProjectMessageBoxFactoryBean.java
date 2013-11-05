package com.bleulace.web.demo.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.Project;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;

@Lazy
@Scope("prototype")
@org.springframework.stereotype.Component("projectMessageBox")
class ProjectMessageBoxFactoryBean implements FactoryBean<MessageBox>,
		MessageBoxListener
{
	private EntityItem<Project> item;

	private Project entity;

	private FieldGroup fieldGroup;

	private MessageBox messageBox;

	@Autowired
	@Qualifier("resourceContainer")
	private JPAContainer<Project> container;

	@Autowired
	private ApplicationContext ctx;

	ProjectMessageBoxFactoryBean()
	{
		this(new Project());
	}

	ProjectMessageBoxFactoryBean(Project project)
	{
		this.entity = project;
	}

	ProjectMessageBoxFactoryBean(EntityItem<Project> item)
	{
		this(item.getEntity());
		this.item = item;
		item.setBuffered(false);
	}

	@PostConstruct
	protected void init()
	{
		fieldGroup = new BeanFieldGroup<Project>(Project.class);
		fieldGroup.setItemDataSource(item == null ? new BeanItem<Project>(
				entity) : item);
	}

	@Override
	public MessageBox getObject() throws Exception
	{
		Component form = (Component) ctx.getBean("projectForm", fieldGroup);
		messageBox = MessageBox.showCustomized(Icon.NONE, "New Project", form,
				this, ButtonId.CANCEL, ButtonId.SAVE).setAutoClose(false);
		return messageBox;
	}

	@Override
	public Class<?> getObjectType()
	{
		return MessageBox.class;
	}

	@Override
	public boolean isSingleton()
	{
		return false;
	}

	@Override
	public void buttonClicked(ButtonId buttonId)
	{
		if (buttonId.equals(ButtonId.SAVE))
		{
			try
			{
				fieldGroup.commit();
				if (item == null)
				{
					container.addEntity(entity);
					container.commit();
				}
				else
				{
					item.commit();
				}
			}
			catch (CommitException e)
			{
				Notification.show("Invalid State");
				return;
			}
		}
		messageBox.close();
	}
}