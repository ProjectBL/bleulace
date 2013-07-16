package com.bleulace.mgt.domain.event;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class BundleAddedEvent extends ProjectCreatedEvent
{
	private static final long serialVersionUID = 3032200342586995114L;

	private String parentId;
}
