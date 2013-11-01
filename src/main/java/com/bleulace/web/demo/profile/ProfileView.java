package com.bleulace.web.demo.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.bleulace.domain.crm.model.ContactInformation;
import com.bleulace.domain.management.model.PersistentEvent;
import com.bleulace.domain.management.model.Project;
import com.bleulace.jpa.EntityManagerReference;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.VaadinView;
import com.bleulace.web.demo.avatar.AvatarFactory;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@VaadinView
class ProfileView extends CustomComponent implements View
{
	@Autowired
	private ProfilePresenter presenter;

	@Autowired
	private AvatarFactory factory;

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private SystemUser user;

	private final Label location = new KeyValueLabel("Location");
	private final Label role = new KeyValueLabel("Role");
	private final Label work = new KeyValueLabel("Work");
	private final Label school = new KeyValueLabel("School");

	private final JPAContainer<Project> projects = makeContainer(Project.class);
	private final JPAContainer<PersistentEvent> events = makeContainer(PersistentEvent.class);

	public ProfileView()
	{
		VerticalLayout left = new VerticalLayout();
		left.addComponents(makeTable("Projects", projects),
				makeTable("Events", events));
		setCompositionRoot(left);
	}

	@Override
	public void enter(ViewChangeEvent event)
	{
		user.setTarget(event.getParameters());
		presenter.init(event.getParameters());
		setCompositionRoot((Component) ctx.getBean("calendar",
				event.getParameters()));
	}

	void setInfo(ContactInformation info)
	{
		location.setCaption(info.getLocation());
		role.setCaption(info.getWork());
		work.setCaption(info.getWork());
		school.setCaption(info.getSchool());
	}

	public JPAContainer<?> getProjects()
	{
		return projects;
	}

	public JPAContainer<?> getEvents()
	{
		return events;
	}

	private Table makeTable(String caption, JPAContainer<?> container)
	{
		Table table = new Table(caption, container);
		return table;
	}

	private <T> JPAContainer<T> makeContainer(Class<T> clazz)
	{
		return JPAContainerFactory.makeNonCachedReadOnly(clazz,
				EntityManagerReference.get());
	}
}