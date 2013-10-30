package com.bleulace.web.demo.calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.infrastructure.AccountDAO;
import com.bleulace.domain.crm.model.Account;
import com.bleulace.utils.CallByName;
import com.bleulace.web.SystemUser;
import com.bleulace.web.annotation.Presenter;
import com.bleulace.web.demo.timebox.ParticipantBean;
import com.google.common.eventbus.EventBus;
import com.vaadin.data.util.BeanContainer;

@Presenter
class VieweePresenter implements CallByName<List<String>>
{
	@Autowired
	private SystemUser user;

	@Autowired
	private AccountDAO accountDAO;

	@Autowired
	private BeanContainer<String, ParticipantBean> vieweeContainer;

	@Autowired
	private EventBus eventBus;

	void accountFocus(Account account)
	{
		vieweeContainer.addBean(new ParticipantBean(account, null));
		eventBus.post(new ViewTargetChangedEvent());
	}

	void accountBlur(String accountId)
	{
		vieweeContainer.removeItem(accountId);
		eventBus.post(new ViewTargetChangedEvent());
	}

	@Override
	public List<String> evaluate()
	{
		String userId = user.getId();
		List<String> strs = new ArrayList<String>(vieweeContainer.getItemIds());
		if (userId != null && strs.remove(userId))
		{
			strs.add(0, userId);
		}
		return Collections.unmodifiableList(strs);
	}
}
