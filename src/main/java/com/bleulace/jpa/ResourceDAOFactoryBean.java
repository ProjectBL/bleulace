package com.bleulace.jpa;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.bleulace.domain.resource.model.AbstractResource;

public class ResourceDAOFactoryBean<R extends JpaRepository<T, String>, T extends AbstractResource>
		extends JpaRepositoryFactoryBean<R, T, String>
{
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em)
	{
		return new ResourceDAOFactory<T>(em);
	}

	private static class ResourceDAOFactory<T extends AbstractResource> extends
			JpaRepositoryFactory
	{
		private final EntityManager em;

		public ResourceDAOFactory(EntityManager em)
		{
			super(em);
			this.em = em;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Object getTargetRepository(RepositoryMetadata metadata)
		{
			return new ResourceDAOBase<T>((Class<T>) metadata.getDomainType(),
					em);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata)
		{
			if (metadata.getDomainType().isInstance(AbstractResource.class))
			{
				return ResourceDAOBase.class;
			}
			return super.getRepositoryBaseClass(metadata);
		}
	}
}