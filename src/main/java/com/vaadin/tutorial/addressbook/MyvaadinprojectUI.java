package com.vaadin.tutorial.addressbook;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Theme("myvaadinproject")
public class MyvaadinprojectUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		//final Button button = new Button("Click Me");
		final TextField textfield = new TextField("Search term");
		final Button searchBtn = new Button("Search");
		
		searchBtn.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				
				 String searchTerm = (String) textfield.getValue();
				 System.out.println("Searched text is "+searchTerm);

				//ImageDataDAO dataDAO = new ImageDataDAO();
				//ImageDataModel datModel = dataDAO.getImageData();

				System.out.println("after connection");

				Table table = new Table("Image Display");
				// Define two columns for the built-in container
				table.addContainerProperty("Index", String.class, null);
				table.addContainerProperty("Entity 1", String.class, null);
				table.addContainerProperty("Relationship", String.class, null);
				table.addContainerProperty("Entity 2", String.class, null);

				// A theme resource in the current theme ("mytheme")
				// Located in: VAADIN/themes/mytheme/img/themeimage.png
				// ThemeResource resource = new ThemeResource(
				// "myvaadinproject/sampleimg.png");

				// Use the resource
				// Image image = new Image("My Theme Image", resource);

				table.addItem(new Object[] { "1", "age", "has", "nonsmoker" },
						1);
				table.addItem(new Object[] { "2", "lifestyle", "has",
						"back pain" }, 2);

				table.addItem(new Object[] { "3", "third image", "has",
						"back pain" }, 3);

				table.setPageLength(table.size());

				for (int i = 0; i < 10; i++) {

//					Table dataTable = new Table(datModel.getCaseName().get(i));
					
					TextArea text = new TextArea();
					text.setValue("one");
					text.setWidth("100%");
					text.setWordwrap(true);
					text.setReadOnly(true);
					layout.addComponent(new Label("two"));
					layout.addComponent(new Label("three"));
					layout.addComponent(text);
				}

				layout.addComponent(table);

				// layout.addComponent(text1);

				// layout.addComponent(text2);

				// layout.addComponent(image1);

				// layout.addComponent(new Label("Thank you for clicking"));
				layout.removeComponent(searchBtn);
				layout.removeComponent(textfield);
			}
		});
		layout.addComponent(textfield);
		layout.addComponent(new Label("&nbsp;", ContentMode.HTML));
		layout.addComponent(searchBtn);
	}
}