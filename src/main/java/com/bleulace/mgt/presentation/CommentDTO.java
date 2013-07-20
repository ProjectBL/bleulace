package com.bleulace.mgt.presentation;

import java.util.Date;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooJavaBean
public class CommentDTO
{
	private String email;
	private String firstName;
	private String lastName;
	private byte[] image;
	private String content;
	private Date datePosted;
}