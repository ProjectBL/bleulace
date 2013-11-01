package com.bleulace.web.demo.timebox;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.data.util.BeanContainer;

@Configuration
class TimeBoxComponentConfig
{
	@Bean
	@Scope("ui")
	public BeanContainer<String, ParticipantBean> eventCandidates(
			@Qualifier("participantBeanContainer") BeanContainer<String, ParticipantBean> container)
	{
		return container;
	}

	@Bean
	@Scope("ui")
	public BeanContainer<String, ParticipantBean> eventParticipants(
			@Qualifier("participantBeanContainer") BeanContainer<String, ParticipantBean> container)
	{
		return container;
	}

	@Bean
	@Scope("prototype")
	public BeanContainer<String, ParticipantBean> makeContainer()
	{
		BeanContainer<String, ParticipantBean> container = new BeanContainer<String, ParticipantBean>(
				ParticipantBean.class);
		container.setBeanIdProperty("id");
		return container;
	}
}