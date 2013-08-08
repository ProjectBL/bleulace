package com.bleulace.crm.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import com.bleulace.mgt.presentation.CommentDTO;

//TODO : finish me!
@RooJavaBean
public class StatusUpdateDTO extends CommentDTO
{
	private List<CommentDTO> comments = new ArrayList<CommentDTO>();
}