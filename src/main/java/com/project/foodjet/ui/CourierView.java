package com.project.foodjet.ui;

import com.project.foodjet.entity.Courier;
import com.project.foodjet.entity.CourierStatus;
import com.project.foodjet.entity.OrderStatus;
import com.project.foodjet.entity.Orders;
import com.project.foodjet.service.CourierService;
import com.project.foodjet.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.List;

@Route("courier")
@RolesAllowed("COURIER")
public class CourierView extends VerticalLayout {

    private final OrderService orderService;
    private final CourierService courierService;

    public CourierView(OrderService orderService, CourierService courierService) {
        this.orderService = orderService;
        this.courierService = courierService;

        // Получение текущего курьера
        Courier currentCourier = getCurrentCourier();

        // Получение списка заказов, не назначенных курьеру
        List<Orders> notAssignedOrders = orderService.findByStatus(OrderStatus.NOT_ASSIGNED);

        // Создание компонента Grid для заказов
        Grid<Orders> orderGrid = new Grid<>(Orders.class);
        orderGrid.setItems(notAssignedOrders);
        orderGrid.setColumns("id", "customer.name", "orderDate", "deliveryDate", "restaurant", "status");

        // Настройка отображения имени заказчика
        orderGrid.getColumnByKey("customer.name").setHeader("Customer").setRenderer(new TextRenderer<>(orders -> orders.getCustomer().getName()));

        // Кнопка для назначения заказа
        orderGrid.addComponentColumn(order -> new Button("Take an order", event -> {
            order.setCourier(currentCourier);
            order.setStatus(OrderStatus.ASSIGNED);
            orderService.update(order);
            currentCourier.setStatus(CourierStatus.EN_ROUTE);
            courierService.update(currentCourier);
            Notification.show("Order assigned successfully!");
            // Обновление списка заказов
            notAssignedOrders.remove(order);
            orderGrid.setItems(notAssignedOrders);
        })).setHeader("Take Order");

        add(new H1("Orders"));
        add(orderGrid);
    }

    private Courier getCurrentCourier() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        if (email != null) {
            return courierService.getByEmail(email);
        }
        return null;
    }
}
