package com.bleulace.domain.crm.ui.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@Configurable(preConstruction = true)
class InfoBlock extends CustomComponent
{
	@Autowired
	@Qualifier("defaultAvatar")
	private Image avatar;

	private final Label name = makeBlockLabel();

	private final BeanContainer<String, InfoBlockEntry> container = makeContainer();

	public InfoBlock()
	{
		avatar.setSizeFull();
		name.setStyleName(Reindeer.LABEL_H1);

		Table infoTable = new Table("", container);
		infoTable.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);

		VerticalLayout layout = new VerticalLayout();
		layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		layout.addComponent(avatar);
		layout.addComponent(name);
		layout.addComponent(infoTable);
		setCompositionRoot(layout);
	}

	public void setName(String name)
	{
		Assert.notNull(name);
		this.name.setCaption(name);
	}

	public void setLocation(String location)
	{
		Assert.notNull(location);
		putInContainer("Location", location);
	}

	public void setWork(String work)
	{
		Assert.notNull(work);
		putInContainer("Work", work);
	}

	public void setSchool(String school)
	{
		Assert.notNull(school);
		putInContainer("School", school);
	}

	private void putInContainer(String key, String value)
	{
		if (container.containsId(key))
		{
			container.removeItem(key);
		}
		container.addBean(new InfoBlockEntry(key, value));
	}

	private BeanContainer<String, InfoBlockEntry> makeContainer()
	{
		BeanContainer<String, InfoBlockEntry> container = new BeanContainer<String, InfoBlockEntry>(
				InfoBlockEntry.class);
		container.setBeanIdProperty("key");
		container.setItemSorter(new DefaultItemSorter());
		return container;
	}

	private Label makeBlockLabel()
	{
		Label l = new Label("");
		l.setStyleName(Reindeer.LABEL_H1);
		l.setSizeUndefined();
		return l;
	}

	private class InfoBlockEntry
	{
		private final String key;
		private final String value;

		public InfoBlockEntry(String key, String value)
		{
			this.key = key;
			this.value = value;
		}

		@SuppressWarnings("unused")
		public String getKey()
		{
			return key;
		}

		@SuppressWarnings("unused")
		public String getValue()
		{
			return value;
		}
	}
}