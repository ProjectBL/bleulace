package net.bluelace.ui.registration;

import net.bluelace.domain.account.AccountRegistrationPayload;

import com.frugalu.api.messaging.annotation.Publish;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;

public class RegistrationForm extends CustomComponent implements ClickListener
{
	private static final long serialVersionUID = 5991966984199415096L;

	private final BeanFieldGroup<AccountRegistrationPayload> fieldGroup = new BeanFieldGroup<AccountRegistrationPayload>(
			AccountRegistrationPayload.class);

	public RegistrationForm()
	{
		fieldGroup.setItemDataSource(new AccountRegistrationPayload());
		setCompositionRoot(new Panel("Register", new FormLayout(
				fieldGroup.buildAndBind("firstName"),
				fieldGroup.buildAndBind("lastName"),
				fieldGroup.buildAndBind("email"), new Button("Submit", this))));
	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		try
		{
			fieldGroup.commit();
			send();
		}
		catch (CommitException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Publish
	private AccountRegistrationPayload send()
	{
		return fieldGroup.getItemDataSource().getBean();
	}
}
