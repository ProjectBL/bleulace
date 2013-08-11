package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import com.bleulace.domain.crm.model.CommentableResource;
import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
public class Bundle extends AbstractChildResource implements
		ManageableResource, CommentableResource
{
}