package com.project.foodjet.ui;

import com.project.foodjet.entity.Role;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;

@Route("register")
@AnonymousAllowed
@RequiredArgsConstructor
public class RegisterView extends Composite {

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField passwordConfirmation = new PasswordField("Password confirmation");
        ComboBox<Role> role = new ComboBox<>("Role");
        role.setItems(Role.CUSTOMER, Role.COURIER);

        return new VerticalLayout(
                new H2(
                        "Register"),
                        username,
                        password,
                        passwordConfirmation,
                        role,
                new Button("Register", event -> register(
                        username.getValue(),
                        password.getValue(),
                        passwordConfirmation.getValue(),
                        role.getValue()
                ))
                );
    }

    private void register(String username, String password, String passwordConfirmation, Role role) {
        if (username.isEmpty()) {
            Notification.show("Enter a username");
        }
        else if (password.isEmpty()) {
            Notification.show("Enter a password");
        }
        else if (!password.equals(passwordConfirmation)) {
            Notification.show("Passwords do not match");
        }
        else {
            Notification.show("User registered successfully");
        }
    }
}