package com.bleulace.web.demo.resource;

import java.util.Set;

import com.vaadin.data.Container;

public interface ResourceQuery<T extends Container>
{
	public Set<String> getManagerIds();

	public void setManagerIds(Set<String> managerIds);

	public T getContainer();
}