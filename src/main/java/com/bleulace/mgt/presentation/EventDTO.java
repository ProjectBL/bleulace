package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.crm.presentation.AccountDTO;
import com.bleulace.utils.ctx.SpringApplicationContext;
import com.bleulace.utils.dto.BasicEventMixin;

@RooToString
@RooEquals
@RooJavaBean
public class EventDTO extends ProjectDTO implements BasicEventMixin
{
	private static final long serialVersionUID = -2448976717235008166L;

	private List<AccountDTO> attendees;

	public static final EventFinder FINDER = SpringApplicationContext
			.getBean(EventFinder.class);
}