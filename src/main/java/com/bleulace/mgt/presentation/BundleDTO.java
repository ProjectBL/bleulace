package com.bleulace.mgt.presentation;

import java.util.List;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.utils.ctx.SpringApplicationContext;

@RooToString
@RooEquals
@RooJavaBean
public class BundleDTO extends ProjectDTO
{
	private List<TaskDTO> tasks;

	public static final BundleFinder FINDER = SpringApplicationContext
			.getBean(BundleFinder.class);
}