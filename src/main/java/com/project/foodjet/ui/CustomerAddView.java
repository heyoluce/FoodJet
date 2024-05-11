package com.project.foodjet.ui;

import com.project.foodjet.entity.Customer;
import com.project.foodjet.service.CustomerService;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route("newCustomer")
@RolesAllowed("ADMIN")
public class CustomerAddView extends VerticalLayout {

    private TextField name = new TextField("name");
    private TextField email = new TextField("email");
    private TextField phone = new TextField("phone");
    private TextField address = new TextField("address");

    public CustomerAddView(CustomerService service) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        var userInfo = new UserInfo(username, username);
        var binder = new CollaborationBinder<>(Customer.class, userInfo);
        binder.bindInstanceFields(this);
        binder.setTopic("New-customer", () -> new Customer());
        var messageList = new CollaborationMessageList(userInfo, "new-customer");
        add(
                new H1("New customer"),

                new HorizontalLayout(
                  new VerticalLayout(
                          new FormLayout(name,email,phone,address),
                          new Button("Save", event -> {
                              var customer = new Customer();
                              binder.writeBeanIfValid(customer);
                              service.add(customer);
                              Notification.show("Customer saved");
                              binder.reset(new Customer());
                          })
                  ),
                        new VerticalLayout(
                                messageList,
                                new CollaborationMessageInput(messageList)
                        )
                )



        );
    }
}
