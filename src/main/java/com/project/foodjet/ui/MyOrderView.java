package com.project.foodjet.ui;

import com.project.foodjet.entity.Courier;
import com.project.foodjet.entity.Customer;
import com.project.foodjet.entity.OrderStatus;
import com.project.foodjet.entity.Orders;
import com.project.foodjet.service.CustomerService;
import com.project.foodjet.service.OrderService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.crudui.crud.impl.GridCrud;


import java.util.List;

@Route("myOrder")
@PermitAll
public class MyOrderView extends VerticalLayout {

    private final OrderService orderService;
    private final CustomerService customerService;

    public MyOrderView(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;



        var crudOrder = new GridCrud<>(Orders.class, orderService);

        Customer customer = getCurrentCustomer();

        crudOrder.getGrid().setColumns("id", "customer.name", "courier.name", "orderDate", "deliveryDate", "restaurant", "status");
        crudOrder.getCrudFormFactory().setVisibleProperties("customer", "courier", "orderDate", "deliveryDate", "restaurant", "status");
        crudOrder.setAddOperationVisible(false);
        crudOrder.setUpdateOperationVisible(false);
        crudOrder.setDeleteOperationVisible(false);

        ListDataProvider<Orders> dataProvider = new ListDataProvider<>(orderService.findByCustomer(customer));
        crudOrder.getGrid().setDataProvider(dataProvider);

        // Настройка отображения имени заказчика
        crudOrder.getGrid().getColumnByKey("customer.name").setHeader("Customer").setRenderer(new TextRenderer<>(orders -> orders.getCustomer().getName()));

        // Настройка отображения имени курьера или "N/A", если курьер не назначен
        crudOrder.getGrid().getColumnByKey("courier.name").setHeader("Courier").setRenderer(new TextRenderer<>(order -> {
            Courier courier = order.getCourier();
            return courier != null ? courier.getName() : "N/A";
        }));

        add(crudOrder);

    }
        private Customer getCurrentCustomer() {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            if (email != null) {
                return customerService.getByEmail(email);
            }
            return null;

        }

}
