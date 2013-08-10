package com.bleulace.domain.management.model;

import javax.persistence.Entity;

import com.bleulace.domain.resource.model.AbstractChildResource;

@Entity
public class Task extends AbstractChildResource implements ManagementResource
{
}