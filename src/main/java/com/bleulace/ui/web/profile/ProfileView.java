package com.bleulace.ui.web.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.ui.infrastructure.VaadinView;
import com.bleulace.ui.web.profile.status.StatusUpdateField;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

@VaadinView
public class ProfileView extends CustomComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2852526000924244915L;

	@Autowired
	private TabSheet profileTabSheet;

	@Override
	public void enter(ViewChangeEvent event)
	{
		String accountId = event.getParameters();
		Assert.notNull(accountId);
		setCompositionRoot(new VerticalLayout(profileTabSheet,
				new StatusUpdateField(accountId), new ProfileHeadingComponent(
						AccountDTO.FINDER.findById(accountId))));
	}
}