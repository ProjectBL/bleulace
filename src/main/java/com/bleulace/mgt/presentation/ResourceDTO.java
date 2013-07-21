package com.bleulace.mgt.presentation;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooJavaBean
public abstract class ResourceDTO
{
	private String id;
	private String title;
	private boolean complete;
}