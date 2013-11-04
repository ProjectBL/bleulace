package com.bleulace.utils;

import java.util.Collections;
import java.util.Set;

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
				public Set<String> evaluate()
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