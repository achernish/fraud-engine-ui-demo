package com.inatec.demo;

import com.inatec.demo.backend.Merchant;
import com.inatec.demo.backend.MerchantService;
import com.vaadin.annotations.*;
import com.vaadin.annotations.JavaScript;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
//import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import javax.servlet.annotation.WebServlet;

/**
 * @author Anatoly Chernysh
 */
@Theme("valo")
public class FraudEngineUI extends UI {

    /*
     * Hundreds of widgets. Vaadin's user interface components are Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    Grid merchantList = new Grid();
    Button newMerchant = new Button("New Merchant");

    // ContactForm is an example of a custom component class
    MerchantForm merchantForm = new MerchantForm();

    // ContactService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    MerchantService service = MerchantService.createDemoService();

    /*
     * The "Main method".
     *
     * This is the entry point method executed to initialize and configure the
     * visible user interface. Executed on every browser reload because a new
     * instance is created for each web page loaded.
     */
    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */
        newMerchant.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                merchantForm.edit(new Merchant());
            }
        });

        filter.setInputPrompt("Filter contacts...");
        filter.addTextChangeListener(new TextChangeListener() {

            @Override
            public void textChange(TextChangeEvent event) {
                refreshContacts(event.getText());
            }
        });

        merchantList.setContainerDataSource(new BeanItemContainer<Merchant>(
                Merchant.class));
        merchantList.setColumnOrder("firstName", "lastName", "email");
        merchantList.removeColumn("id");
        merchantList.removeColumn("birthDate");
        merchantList.removeColumn("phone");
        merchantList.setSelectionMode(Grid.SelectionMode.SINGLE);
        merchantList.addSelectionListener(new SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
                merchantForm.edit((Merchant) merchantList.getSelectedRow());
            }
        });
        refreshContacts();
    }

    /*
     * Robust layouts.
     *
     * Layouts are components that contain other components. HorizontalLayout
     * contains TextField and Button. It is wrapped with a Grid into
     * VerticalLayout for the left side of the screen. Allow user to resize the
     * components with a SplitPanel.
     *
     * In addition to programmatically building layout in Java, you may also
     * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
     */
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newMerchant);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, merchantList);
        left.setSizeFull();
        merchantList.setSizeFull();
        left.setExpandRatio(merchantList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, merchantForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        setContent(mainLayout);

    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split
     * your code into classes to easier maintenance. With Vaadin you can follow
     * MVC, MVP or any other design pattern you choose.
     */
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        merchantList.setContainerDataSource(new BeanItemContainer<Merchant>(
                Merchant.class, service.findAll(stringFilter)));
        merchantForm.setVisible(false);
    }

    /*
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class
     * name and turn on production mode when you have finished developing the
     * application.
     */
    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = FraudEngineUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}