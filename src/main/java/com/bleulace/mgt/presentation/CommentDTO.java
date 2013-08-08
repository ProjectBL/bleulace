package com.bleulace.mgt.presentation;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.bleulace.crm.presentation.AccountDTO;

@RooToString
@RooJavaBean
public class CommentDTO
{
	private AccountDTO author;
	private String content;
	private Date datePosted;
}