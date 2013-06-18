package net.bluelace.ui.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

@Configurable
public class BluelaceWebUI extends UI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191009197124553972L;

	@Autowired
	private transient ApplicationContext ctx;

	@Override
	protected void init(VaadinRequest request)
	{
		setContent(WelcomeView.class);
		ctx.getBeanNamesForType(View.class);
	}

	protected void setContent(Class<? extends Component> beanClazz)
	{
		setContent(ctx.getBean(beanClazz));
	}
}