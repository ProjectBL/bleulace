package com.bleulace.domain.management.ui.calendar.modal;

import com.bleulace.domain.management.ui.calendar.model.EventModel;
import com.vaadin.ui.Window;

public interface ModalFactory
{
	public Window make(EventModel model);
}