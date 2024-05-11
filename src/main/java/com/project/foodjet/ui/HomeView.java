package com.project.foodjet.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Route("")
@RolesAllowed({"CUSTOMER", "ADMIN", "COURIER"})
public class HomeView extends VerticalLayout {



    public HomeView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSpacing(true);

        H1 title = new H1("Welcome to FoodJet");
        Button loginButton = new Button("Logout", event -> getUI().ifPresent(ui -> ui.navigate("login")));
        add(title, loginButton);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<?> authorities = userDetails.getAuthorities();

        for (Object authority : authorities) {
            String authorityName = authority.toString();

            if (authorityName.equals("ROLE_CUSTOMER")) {
                Button myOrdersButton = new Button("My Orders", event -> getUI().ifPresent(ui -> ui.navigate("myOrder")));
                Button makeOrderButton = new Button("Make Order", event -> getUI().ifPresent(ui -> ui.navigate("orders")));
                add(myOrdersButton, makeOrderButton);
            }

            if (authorityName.equals("ROLE_ADMIN")) {
                Button adminViewButton = new Button("Admin View", event -> getUI().ifPresent(ui -> ui.navigate("admin")));
                Button reportButton = new Button("Report", event -> getUI().ifPresent(ui -> ui.navigate("report")));
                add(adminViewButton, reportButton);
            }

            if (authorityName.equals("ROLE_COURIER")) {
                Button courierViewButton = new Button("Take an order", event -> getUI().ifPresent(ui -> ui.navigate("courier")));
                add(courierViewButton);
            }
        }


    }
}
