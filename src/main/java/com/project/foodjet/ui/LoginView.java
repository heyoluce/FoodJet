package com.project.foodjet.ui;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends Composite<LoginOverlay> {

    public LoginView() {
        getContent().setOpened(true);
        getContent().setAction("login");
        getContent().setTitle("FoodJet");
        getContent().setDescription("Online delivery");

    }
}
