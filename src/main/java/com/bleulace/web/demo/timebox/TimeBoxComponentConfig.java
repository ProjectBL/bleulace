package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bleulace.domain.management.model.PersistentEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;

@Configuration
class TimeBoxComponentConfig
{
	@Autowired
	private TimeBoxPresenter presenter;

	@Bean
	@Scope("ui")
	public BeanContainer<String, ParticipantBean> candidates()
	{
		return makeContainer();
	}

	@Bean
	@Scope("ui")
	public BeanContainer<String, ParticipantBean> participants()
	{
		return makeContainer();
	}

	@Bean
	@Scope("ui")
	public BeanFieldGroup<PersistentEvent> timeBoxFieldGroup()
	{
		BeanFieldGroup<PersistentEvent> bean = new BeanFieldGroup<PersistentEvent>(
				PersistentEvent.class);
		bean.addCommitHandler(presenter);
		return bean;
	}

	private BeanContainer<String, ParticipantBean> makeContainer()
	{
		BeanContainer<String, ParticipantBean> container = new BeanContainer<String, ParticipantBean>(
				ParticipantBean.class);
		container
				.setBeanIdResolver(new BeanIdResolver<String, ParticipantBean>()
				{
					@Override
					public String getIdForBean(ParticipantBean bean)
					{
						return bean.getId();
					}
				});
		return container;
	}
}