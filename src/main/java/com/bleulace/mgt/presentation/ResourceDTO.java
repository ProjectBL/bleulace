package com.bleulace.mgt.presentation;

import java.util.List;

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

	private List<CommentDTO> comments;
}