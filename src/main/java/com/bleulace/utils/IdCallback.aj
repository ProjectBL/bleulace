package com.bleulace.utils;

import java.util.Collection;
import java.util.Collections;

public interface IdCallback
{
	public String evaluate();

	static aspect Impl
	{
		public IdsCallback IdCallback.asIdsCallback()
		{
			return new IdsCallback()
			{
				@Override
				public Collection<String> evaluate()
				{
					return Collections.singleton(eval());
				}
			};
		}

		private String IdCallback.eval()
		{
			return this.evaluate();
		}
	}

}