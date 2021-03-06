package com.inatec.demo;

import com.inatec.demo.backend.Merchant;
import com.inatec.demo.xtext.XTextEditor;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Anatoly Chernysh
 */
public class MerchantForm extends FormLayout implements ClickListener {

    Button save = new Button("Save", this);
    Button cancel = new Button("Cancel", this);

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phone = new TextField("Phone");
    TextField email = new TextField("Email");
    DateField birthDate = new DateField("Birth date");
    XTextEditor xTextEditor = new XTextEditor();

    Merchant merchant;

    // Easily bind forms to beans and manage validation and buffering
    BeanFieldGroup<Merchant> formFieldBindings;

    public MerchantForm() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         * and give it a keyboard shortcut for a better UX.
         */
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);

        addComponents(actions, firstName, lastName, phone, email, birthDate, xTextEditor);
    }

    void edit(Merchant merchant) {
        this.merchant = merchant;
        if (merchant != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup
                    .bindFieldsBuffered(merchant, this);

            xTextEditor.setValue(merchant.getRule());

            firstName.focus();
        }
        setVisible(merchant != null);
    }

    @Override
    public FraudEngineUI getUI() {
        return (FraudEngineUI) super.getUI();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == save) {
            try {
                // Commit the fields from UI to DAO
                formFieldBindings.commit();
                merchant.setRule(xTextEditor.getValue());

                // Save DAO to backend with direct synchronous service API
                getUI().service.save(merchant);

                String msg = String.format("Saved '%s %s'.",
                        merchant.getFirstName(), merchant.getLastName());
                Notification.show(msg, Type.TRAY_NOTIFICATION);
                getUI().refreshMerchants();
            } catch (FieldGroup.CommitException e) {
                // Validation exceptions could be shown here
            }
        } else if (event.getButton() == cancel) {
            // Place to call business logic.
            Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
            getUI().merchantList.select(null);
        }
    }
}