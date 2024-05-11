package com.project.foodjet.ui;

import com.project.foodjet.entity.Courier;
import com.project.foodjet.entity.CourierStatus;
import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.Orders;
import com.project.foodjet.service.CourierService;
import com.project.foodjet.service.CustomerService;
import com.project.foodjet.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.context.annotation.Bean;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {


    public AdminView(CustomerService customerService,
                     CourierService courierService,
                     OrderService orderService) {
        var crud = new GridCrud<>(Customer.class, customerService);
        crud.getGrid().setColumns("id", "name", "email", "address", "phone");
        crud.getCrudFormFactory().setVisibleProperties( "name", "email", "address", "phone");
        crud.setAddOperationVisible(false);

        Button addCustomerButton = new Button("Add customer", event -> getUI().ifPresent(ui ->
                ui.navigate(CustomerAddView.class)));

        crud.getCrudLayout().addToolbarComponent(addCustomerButton);


        var crudCourier = new GridCrud<>(Courier.class, courierService);
        crudCourier.getGrid().setColumns("id", "name", "email", "phone", "status");
        crud.getCrudFormFactory().setVisibleProperties( "name", "email", "phone");
        crudCourier.setAddOperationVisible(false);


        Button addCourierButton = new Button("Add courier", event -> getUI().ifPresent(ui ->
                ui.navigate(CourierAddView.class)));

        crudCourier.getCrudLayout().addToolbarComponent(addCourierButton);



        var crudOrder = new GridCrud<>(Orders.class, orderService);
        crudOrder.getGrid().setColumns("id", "customer.name", "courier.name", "orderDate", "deliveryDate", "restaurant", "status");
        crudOrder.getCrudFormFactory().setVisibleProperties("customer", "courier", "orderDate", "deliveryDate", "restaurant", "status");

        crudOrder.getGrid().getColumnByKey("customer.name").setHeader("Customer").setRenderer(new TextRenderer<>(orders -> orders.getCustomer().getName()));
        crudOrder.getGrid().getColumnByKey("courier.name").setHeader("Courier").setRenderer(new TextRenderer<>(order -> {
            Courier courier = order.getCourier();
            return courier != null ? courier.getName() : "N/A";
        }));

        add(
                new H1("Admin View"),
                new H2("Customers"),
                crud,
                new H2("Couriers"),
                crudCourier,
                new H2("Orders"),
                crudOrder
                );
    }


}
