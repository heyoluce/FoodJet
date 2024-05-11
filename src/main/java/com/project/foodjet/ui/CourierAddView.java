package com.project.foodjet.ui;

import com.project.foodjet.entity.Courier;
import com.project.foodjet.entity.CourierStatus;
import com.project.foodjet.entity.Customer;
import com.project.foodjet.service.CourierService;
import com.project.foodjet.service.CustomerService;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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

@Route("newCourier")
@RolesAllowed("ADMIN")
public class CourierAddView extends VerticalLayout {

    private TextField name = new TextField("name");
    private TextField email = new TextField("email");
    private TextField phone = new TextField("phone");
    private ComboBox<CourierStatus> status = new ComboBox<>("status");

    public CourierAddView(CourierService service) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        var userInfo = new UserInfo(username, username);
        var binder = new CollaborationBinder<>(Courier.class, userInfo);
        binder.bindInstanceFields(this);
        binder.setTopic("New-courier", () -> new Courier());
        var messageList = new CollaborationMessageList(userInfo, "new-courier");

        status.setItems(CourierStatus.values());

        add(
                new H1("New courier"),

                new HorizontalLayout(
                        new VerticalLayout(
                                new FormLayout(name,email,phone, status),
                                new Button("Save", event -> {
                                    var courier = new Courier();
                                    binder.writeBeanIfValid(courier);
                                    service.add(courier);
                                    Notification.show("Courier saved");
                                    binder.reset(new Courier());
                                })
                        ),
                        new VerticalLayout(
                                messageList,
                                new CollaborationMessageInput(messageList)
                        )
                )



        );

    }
    private Object getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        if (email != null) {
        }
        return null;

    }
}
