package com.bleulace.domain.management.presentation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Persistable;

import com.bleulace.IntegrationTest;
import com.bleulace.cqrs.command.CommandGatewayAware;
import com.bleulace.domain.crm.command.AccountManager;
import com.bleulace.domain.management.command.AssignManagersCommand;
import com.bleulace.domain.management.command.ManagementCommandFactory;
import com.bleulace.domain.management.model.Bundle;
import com.bleulace.domain.management.model.Event;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.Project;
import com.bleulace.utils.Locator;
import com.bleulace.utils.dto.Mapper;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent.EventChangeListener;

public class ManagementDTOTest implements IntegrationTest, EventChangeListener,
		CommandGatewayAware
{
	@Autowired
	private ManagementCommandFactory factory;

	@Autowired
	private Validator validator;

	private boolean eventCaught = false;

	private final static ManagementLevel LEVEL = ManagementLevel.MIX;

	@Before
	public void setUp()
	{
		AccountManager m = new AccountManager().login();
		factory.initializeDefaultGroup();
		sendAndWait(new AssignManagersCommand(load(Project.class).getId(),
				LEVEL, m.getId()));
	}

	@Test
	public void testProjectDTO()
	{
		ProjectDTO dto = validateMapping(Project.class, ProjectDTO.class);
		Assert.assertTrue(dto.getBundles().size() > 0);
		Assert.assertTrue(dto.getManagers().size() > 0);
	}

	@Test
	public void testBundleDTO()
	{
		BundleDTO dto = validateMapping(Bundle.class, BundleDTO.class);
		Assert.assertTrue(dto.getTasks().size() > 0);
	}

	@Test
	public void testEventDTO()
	{
		factory.createEvent();
		EventDTO dto = validateMapping(Event.class, EventDTO.class);
		dto.addEventChangeListener(this);
		dto.setCaption("foo");
		Assert.assertTrue(eventCaught);
	}

	@Override
	public void eventChange(EventChangeEvent eventChangeEvent)
	{
		eventCaught = true;
	}

	private <S extends Persistable<String>, T> T validateMapping(Class<S> from,
			Class<T> to)
	{
		T value = Mapper.map(load(from), to);
		for (ConstraintViolation<T> v : validator.validate(value))
		{
			Assert.fail(v.toString());
		}
		return value;
	}

	private <T extends Persistable<String>> T load(Class<T> clazz)
	{
		return Locator.locate(clazz);
	}
}