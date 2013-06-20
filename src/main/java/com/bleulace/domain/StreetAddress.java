package com.bleulace.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@Embeddable
@RooEquals
@RooToString
@RooJavaBean
public class StreetAddress
{
	private Integer streetNumber;
	private String streetName;
	private String city;

	@Enumerated(EnumType.STRING)
	private UsStatesEnum state;

	private Integer zip;

	private String phoneNumber;

	public enum UsStatesEnum
	{
		//@formatter:off
		AL("Alabama"),
		AK("Alaska"),
		AZ("Arizona"),
		AR("Arkansas"),
		CA("California"),
		CO("Colorado"),
		CT("Connecticut"),
		DE("Delaware"),
		FL("Florida"),
		GA("Georgia"),
		HI("Hawaii"),
		ID("Idaho"),
		IL("Illinois"),
		IN("Indiana"),
		IA("Iowa"),
		KS("Kansas"),
		KY("Kentucky"),
		LA("Louisiana"),
		ME("Maine"),
		MD("Maryland"),
		MA("Massuchusets"),
		MI("Michigan"),
		MN("Minnesota"),
		MS("Mississipi"),
		MO("Missouri"),
		MT("Montana"),
		NE("Nebraska"),
		NV("Nevada"),
		NH("New Hampshire"),
		NJ("New Jersey"),
		NM("New Mexico"),
		NY("New York"),
		NC("North California"),
		ND("North Dakota"),
		OH("Ohio"),
		OK("Oklahoma"),
		OR("Oregon"),
		PA("Pennsylvania"),
		RI("Rhode Island"),
		SC("South California"),
		SD("South Dakota"),
		TN("Tennessee"),
		TX("Texas"),
		UT("Utah"),
		VT("Vermont"),
		VA("Virginia"),
		WA("Washington"),
		WV("West Virginia"),
		WI("Wisconsin"),
		WY("Wyoming");
		//@formatter:on

		private final String displayName;

		private UsStatesEnum(String displayName)
		{
			this.displayName = displayName;
		}

		@Override
		public String toString()
		{
			return displayName;
		}

		public static UsStatesEnum fromString(String str)
		{
			UsStatesEnum match = null;
			if (str != null)
			{
				str = str.replace(" ", "");
				try
				{
					match = UsStatesEnum.valueOf(str.toUpperCase());
				}
				catch (IllegalArgumentException ex)
				{
					for (UsStatesEnum e : UsStatesEnum.values())
					{
						String stateString = e.toString().replace(" ", "");
						if (stateString.equalsIgnoreCase(str))
						{
							match = e;
							break;
						}
					}
				}
			}
			return match;
		}
	}
}