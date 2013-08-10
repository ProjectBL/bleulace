package com.bleulace.domain.crm.model;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@Embeddable
@RooJavaBean
@RooEquals
@RooToString
@RooSerializable
public class ContactInformation
{
	private String firstName = "";
	private String lastName = "";
	private String email = "";
	private String school = "";
	private String work = "";
}