package com.bleulace.web.demo.profile;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.domain.crm.model.Account;
import com.bleulace.domain.crm.model.ContactInformation;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@Scope("prototype")
@Component("profileInfo")
class ProfileInfoComponent extends CustomComponent
{
	ProfileInfoComponent(Account account)
	{
		VerticalLayout layout = new VerticalLayout();

		Label name = new Label(account.getContactInformation().getName());
		name.addStyleName(Reindeer.LABEL_H1);
		layout.addComponent(name);

		for (Entry<String, String> entry : getPropsMap(account).entrySet())
		{
			layout.addComponent(new KeyValueLabel(entry));
		}
		setCompositionRoot(layout);
	}

	@SuppressWarnings("unused")
	private ProfileInfoComponent()
	{
	}

	private Map<String, String> getPropsMap(Account account)
	{
		ContactInformation info = account.getContactInformation();
		Builder<String, String> builder = ImmutableMap.builder();
		builder.put("Location", info.getLocation());
		builder.put("Work", info.getWork());
		builder.put("Role", "NOT IMPLEMENTED");
		builder.put("School", info.getSchool());
		return builder.build();
	};
}