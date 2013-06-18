package net.bluelace.ui.mobile;

import net.bluelace.domain.account.Account;

import com.vaadin.addon.touchkit.gwt.client.theme.StyleNames;
import com.vaadin.addon.touchkit.ui.DatePicker;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RegistrationView extends NavigationView implements ClickListener
{
	private static final long serialVersionUID = 5991966984199415096L;

	private final BeanFieldGroup<Account> fieldGroup = new BeanFieldGroup<Account>(
			Account.class);

	public RegistrationView()
	{
		super("Register for BlueLace");
		setLeftComponent(new Button("Cancel"));
		fieldGroup.setItemDataSource(new Account());
		fieldGroup.setBuffered(false);

		VerticalComponentGroup required = new VerticalComponentGroup("Required");
		required.addComponents(fieldGroup.buildAndBind("firstName"), fieldGroup
				.buildAndBind("lastName"), fieldGroup.buildAndBind("email"),
				fieldGroup.buildAndBind("Password", "password",
						PasswordField.class));

		VerticalComponentGroup optional = new VerticalComponentGroup("Optional");
		optional.addComponent(new NumberField("Phone"));
		optional.addComponent(new TextField("Location"));
		optional.addComponent(new DatePicker("Birthday"));

		Button register = new Button("Register", this);
		register.addStyleName(StyleNames.BUTTON_BLUE);
		register.setSizeFull();

		setContent(new VerticalLayout(required, optional, register));

	}

	@Override
	public void buttonClick(ClickEvent event)
	{
		if (fieldGroup.isValid())
		{
			Account account = (Account) fieldGroup.getItemDataSource()
					.getBean().save();
			if (account != null)
			{
				fieldGroup.setItemDataSource(account);
			}
		}
	}
}
