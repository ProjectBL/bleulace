package com.bleulace.domain.crm.ui.profile;

import org.apache.shiro.SecurityUtils;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.crm.presentation.UserFinder;
import com.bleulace.domain.management.presentation.EventFinder;
import com.bleulace.domain.management.presentation.ProjectFinder;
import com.bleulace.web.Presenter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@Presenter(viewNames = "profileView")
class ProfilePresenter
{
	private String ownerId;
	private String viewerId;

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
	}
}