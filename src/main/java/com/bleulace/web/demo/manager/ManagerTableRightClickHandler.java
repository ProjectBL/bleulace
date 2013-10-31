package com.bleulace.web.demo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bleulace.domain.management.model.ManagementLevel;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;

@Component
class ManagerTableRightClickHandler implements Handler
{
	@Autowired
	private ManagerBoxPresenter presenter;

	//@formatter:off
	private final Action[] actions = new Action[] { 
			new ManagerAction(ManagementLevel.LOOP, "Loop"),
			new ManagerAction(ManagementLevel.MIX, "Mix"),
			new ManagerAction(ManagementLevel.OWN, "Own"),
	};
	//@formatter:on

	@Override
	public Action[] getActions(Object target, Object sender)
	{
		return actions;
	}

	@Override
	public void handleAction(Action action, Object sender, Object target)
	{
		Assert.notNull(target);
		String id = (String) target;
		if (action instanceof ManagerAction)
		{
			ManagementLevel level = ((ManagerAction) action).level;
			presenter.managerUpdated(id, level);
		}
	}

	class ManagerAction extends Action
	{
		private final ManagementLevel level;

		ManagerAction(ManagementLevel level, String caption)
		{
			super(caption);
			this.level = level;
		}
	}
}