package com.bleulace.domain.crm.ui.profile;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.crm.presentation.UserFinder;
import com.bleulace.domain.crm.ui.profile.ResourceListComponent.ResourceSelectedEvent;
import com.bleulace.domain.management.presentation.EventFinder;
import com.bleulace.domain.management.presentation.ManageableResourceDTO;
import com.bleulace.domain.management.presentation.ProjectFinder;
import com.bleulace.web.stereotype.Presenter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;

@Presenter(viewNames = "profileView")
class ProfilePresenter
{
	private String ownerId;
	private String viewerId;

	private static final IdTitleMapConverter CONVERTER = new IdTitleMapConverter();

	@Autowired
	private transient ProfileView view;

	@Autowired
	private transient ProjectFinder projectFinder;

	@Autowired
	private transient EventFinder eventFinder;

	@Autowired
	private transient UserFinder userFinder;

	@EventHandler
	public void on(ViewChangeEvent event)
	{
		viewerId = SecurityUtils.getSubject().getId();
		ownerId = event.getParameters();

		Assert.notNull(viewerId);
		Assert.notNull(ownerId);

		UserDTO userDTO = userFinder.findById(ownerId);
		Assert.notNull(userDTO);

		view.setInfo(userDTO);

		view.setProjects(CONVERTER.convert(projectFinder.findByManager(ownerId)));

		LocalDateTime now = LocalDateTime.now();
		view.setEvents(CONVERTER.convert(eventFinder.findByAccountIdForDates(
				ownerId, now.toDate(), now.plusYears(1).toDate())));
	}

	@EventHandler
	public void on(ResourceSelectedEvent event)
	{
		Notification.show("Resource selected with id: '"
				+ event.getResourceId() + "'");
	}

	private static class IdTitleMapConverter
			implements
			Converter<Iterable<? extends ManageableResourceDTO>, Map<String, String>>
	{
		@Override
		public Map<String, String> convert(
				Iterable<? extends ManageableResourceDTO> source)
		{
			Map<String, String> map = new HashMap<String, String>();
			for (ManageableResourceDTO dto : source)
			{
				map.put(dto.getId(), dto.getCaption());
			}
			return map;
		}
	}
}