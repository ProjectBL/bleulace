package net.bluelace.domain.calendar;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;

import net.bluelace.domain.account.Account;

import com.vaadin.addon.jpacontainer.util.DefaultQueryModifierDelegate;

class BLQueryModifierDelegate extends DefaultQueryModifierDelegate
{
	private static final long serialVersionUID = 7745356154186607267L;

	private final Account account;

	BLQueryModifierDelegate(Account account)
	{
		this.account = account;
	}

	@Override
	public void queryHasBeenBuilt(CriteriaBuilder builder,
			CriteriaQuery<?> query)
	{
		Root<CalendarEntry> entries = query.from(CalendarEntry.class);
		MapJoin<CalendarEntry, Account, ParticipationStatus> participants = entries
				.joinMap("participants");
		query.where(builder.equal(participants.key(), account));
	}
}