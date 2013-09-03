package com.bleulace.domain.crm.ui.profile.field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bleulace.domain.crm.presentation.UserDTO;
import com.bleulace.domain.crm.presentation.UserFinder;
import com.bleulace.domain.management.model.ManagementLevel;
import com.bleulace.domain.management.model.RsvpStatus;
import com.bleulace.domain.management.presentation.EventDTO;
import com.bleulace.web.stereotype.UIComponent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;

@UIComponent
class ParticipantFieldMediator implements ParticipantFieldSharedState,
		ParticipantFieldOperations
{
	@Autowired
	private BeanContainer<String, UserDTODecorator> candidateContainer;

	@Autowired
	private BeanContainer<String, UserDTODecorator> participantContainer;

	@Autowired
	private UserFinder finder;

	public void setEvent(EventDTO event)
	{
		candidateContainer.removeAllItems();
		participantContainer.removeAllItems();
		for (UserDTO dto : finder.findAll())
		{
			RsvpStatus status = event.getRsvpStatus(dto.getId());
			ManagementLevel level = event.getManagers().get(dto.getId());
			candidateContainer
					.addBean(new UserDTODecorator(dto, status, level));
			if (status != null)
			{
				guestInvited(dto.getId());
				if (level != null)
				{
					managerAdded(dto.getId(), level);
				}
			}
		}
	}

	@Override
	public void guestInvited(String id)
	{
		moveItem(candidateContainer, participantContainer, id);
		BeanItem<UserDTODecorator> item = participantContainer.getItem(id);

		if (item.getBean().getStatus() == null)
		{
			item.getItemProperty("status").setValue(RsvpStatus.PENDING);
		}
	}

	@Override
	public void guestRemoved(String id)
	{
		updateAccessLevel(id, null);
		moveItem(participantContainer, candidateContainer, id);
	}

	@Override
	public void managerAdded(String id, ManagementLevel level)
	{
		updateAccessLevel(id, level);
	}

	@Override
	public void managerRemoved(String id)
	{
		updateAccessLevel(id, null);
	}

	@Override
	public Set<String> getParticipantIds()
	{
		return new HashSet<String>(participantContainer.getItemIds());
	}

	@Override
	public void setParticipantIds(Set<String> ids)
	{
		for (String id : ids)
		{
			guestInvited(id);
		}
	}

	@Override
	public Map<String, ManagementLevel> getManagerIds()
	{
		Map<String, ManagementLevel> map = new HashMap<String, ManagementLevel>();
		for (String id : participantContainer.getItemIds())
		{
			UserDTODecorator dto = participantContainer.getItem(id).getBean();
			if (dto.getLevel() != null)
			{
				map.put(id, dto.getLevel());
			}
		}
		return map;
	}

	@Override
	public void setManagerIds(Map<String, ManagementLevel> map)
	{
		for (Entry<String, ManagementLevel> entry : map.entrySet())
		{
			if (entry.getValue() != null)
			{
				managerAdded(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public BeanContainer<String, UserDTODecorator> getParticipantContainer()
	{
		return participantContainer;
	}

	@Override
	public BeanContainer<String, UserDTODecorator> getCandidateContainer()
	{
		return candidateContainer;
	}

	@SuppressWarnings("unchecked")
	private void updateAccessLevel(String id, ManagementLevel level)
	{
		guestInvited(id);
		{
			participantContainer.getItem(id).getItemProperty("level")
					.setValue(level);
		}
	}

	private void moveItem(BeanContainer<String, UserDTODecorator> source,
			BeanContainer<String, UserDTODecorator> target, String id)
	{
		if (source.containsId(id))
		{
			UserDTODecorator dto = source.getItem(id).getBean();
			source.removeItem(id);
			if (!target.containsId(dto.getId()))
			{
				target.addBean(dto);
			}
		}
	}
}
