package com.bleulace.web.demo.shared;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bleulace.web.demo.shared.OpenResourceDialogue.OpenResourceListener;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;

@Lazy
@Scope("prototype")
@Component("openDialogue")
class OpenResourceMessageBox implements FactoryBean<MessageBox>,
		MessageBoxListener, ItemClickListener
{
	private final Table table;
	private final JPAContainer<?> container;
	private final List<OpenResourceListener> listeners;
	private final String title;
	private MessageBox messageBox;

	private OpenResourceMessageBox()
	{
		this(null, null, null);
	}

	OpenResourceMessageBox(String title, JPAContainer<?> container,
			List<OpenResourceListener> listeners)
	{
		this.listeners = listeners;
		this.title = title;
		this.container = container;
		table = makeTable();
	}

	@Override
	public MessageBox getObject() throws Exception
	{
		this.messageBox = MessageBox.showCustomized(Icon.NONE, title, table,
				this, ButtonId.CANCEL, ButtonId.OK);
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
		if (buttonId.equals(ButtonId.OK))
		{
			fireResourceOpenedEvent();
		}
	}

	@Override
	public void itemClick(ItemClickEvent event)
	{
		if (event.isDoubleClick())
		{
			fireResourceOpenedEvent();
		}
	}

	private void fireResourceOpenedEvent()
	{
		Object id = table.getValue();
		if (id != null)
		{
			for (OpenResourceListener l : listeners)
			{
				l.resourceOpened(container.getItem(id));
			}
			messageBox.close();
		}
	}

	private Table makeTable()
	{
		Table table = new Table();
		if (container != null)
		{
			table.setContainerDataSource(container);
			table.setVisibleColumns(new Object[] { "title", "lastModifiedDate" });
		}
		table.addItemClickListener(this);
		table.setSelectable(true);
		table.setNullSelectionAllowed(false);
		return table;
	}
}
