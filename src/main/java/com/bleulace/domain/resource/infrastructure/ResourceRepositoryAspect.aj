package com.bleulace.domain.resource.infrastructure;

import java.util.List;
import java.util.Set;

import org.axonframework.domain.AggregateRoot;
import org.axonframework.domain.EventMessage;
import org.axonframework.unitofwork.CurrentUnitOfWork;
import org.axonframework.unitofwork.UnitOfWork;
import org.axonframework.unitofwork.UnitOfWorkListener;

import com.bleulace.cqrs.SaveResourceCallback;
import com.bleulace.domain.resource.model.AbstractResource;
import com.bleulace.domain.resource.model.ChildCollectingResourceInspector;

aspect ResourceRepositoryAspect
{
	before(AbstractResource resource) : execution(* AbstractRepository+.doSave(*)) && args(resource)
	{
		CurrentUnitOfWork.get().registerListener(new UnitOfWorkListener()
		{
			
			@Override
			public void onRollback(UnitOfWork unitOfWork, Throwable failureCause)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPrepareTransactionCommit(UnitOfWork unitOfWork,
					Object transaction)
			{
				ChildCollectingResourceInspector i= new ChildCollectingResourceInspector();
			}
			
			@Override
			public void onPrepareCommit(UnitOfWork unitOfWork,
					Set<AggregateRoot> aggregateRoots, List<EventMessage> events)
			{
				new ChildCollectingResourceInspector();
			}
			
			@Override
			public <T> EventMessage<T> onEventRegistered(UnitOfWork unitOfWork,
					EventMessage<T> event)
			{
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void onCleanup(UnitOfWork unitOfWork)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterCommit(UnitOfWork unitOfWork)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
}
