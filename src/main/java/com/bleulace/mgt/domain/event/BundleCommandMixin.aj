package com.bleulace.mgt.domain.event;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import com.bleulace.mgt.domain.ProjectFinder;
import com.bleulace.utils.SpringApplicationContext;

public interface BundleCommandMixin
{
	static aspect Impl
	{
		@TargetAggregateIdentifier
		private String BundleCommandMixin.projectId;

		private String BundleCommandMixin.bundleId;

		public String BundleCommandMixin.getBundleId()
		{
			return bundleId;
		}

		private void BundleCommandMixin.setBundleId(String bundleId)
		{
			this.bundleId = bundleId;
			String projectId = SpringApplicationContext.get()
					.getBean(ProjectFinder.class).findIdFromBundleId(bundleId);
			if (projectId == null)
			{
				throw new IllegalArgumentException("bundleId '" + bundleId
						+ "' is not associated with any projects");
			}
			this.projectId = projectId;
		}
	}
}
