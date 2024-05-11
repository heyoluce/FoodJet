package com.project.foodjet.ui;

import com.project.foodjet.entity.*;
import com.project.foodjet.service.CartService;
import com.project.foodjet.service.CustomerService;
import com.project.foodjet.service.ItemService;
import com.project.foodjet.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServlet;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Route("orders")
@RolesAllowed("CUSTOMER")
public class OrderView extends VerticalLayout {

    private final CustomerService customerService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final CartService cartService;


    public OrderView(CustomerService customerService, ItemService itemService, OrderService orderService, CartService cartService) {
        this.customerService = customerService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.cartService = cartService;

        List<Item> items = getAllItems(); // Получаем все товары

        for (Item item : items) {
            Button addToCartButton = new Button("Add to Cart", event -> {

                Customer customer = getCurrentCustomer();
                Cart currentCart = cartService.getByCustomerAndStatus(customer, CartStatus.ACTIVE);

                if (customer != null) {

                    if (currentCart != null) {
                        cartService.addItem(currentCart, item.getId());
                    }
                    else {
                        Cart cart = new Cart();
                        cart.setStatus(CartStatus.ACTIVE);
                        cart.setCustomer(customer);
                        cartService.add(cart);
                        cartService.addItem(cart, item.getId());
                    }
                    Notification.show("Item added to cart");
                } else {
                    Notification.show("Unable to find current customer");
                }
            });


            VerticalLayout foodLayout = createFoodLayout(item);

            // Добавляем кнопку "Add to Cart" и макет с товаром на страницу
            foodLayout.add(addToCartButton);
            add(foodLayout);

        }
        Button checkoutButton = new Button("Make order", event -> {
            Customer customer = getCurrentCustomer();
            if (customer != null && cartService.getByCustomerAndStatus(customer, CartStatus.ACTIVE) != null) {
               Orders order = new Orders();
                order.setCustomer(customer);
                order.setOrderDate(LocalDateTime.now());
                order.setCart(cartService.getByCustomerAndStatus(customer, CartStatus.ACTIVE));
                order.setRestaurant("KFC");
                order.setStatus(OrderStatus.NOT_ASSIGNED);
                orderService.add(order);
                Notification.show("Order successfully accepted");
                cartService.clearCart(customer);
            } else {
                Notification.show("Cart is empty. Add items before making order");
            }
        });

        // Кнопка очистки корзины
        Button clearCartButton = new Button("Clear cart", event -> {
            Customer customer = getCurrentCustomer();
            if (customer != null) {
                cartService.clearCart(customer);
                Notification.show("Cart is cleared");
            } else {
                Notification.show("Cart is already empty");
            }
        });

        add(checkoutButton, clearCartButton);
    }

    private List<Item> getAllItems() {

        return itemService.findAll();
    }

    private Image createFoodImage(String imageName) {
        InputStreamFactory inputStreamFactory = () -> VaadinServlet.class.getResourceAsStream("/images/" + imageName);
        StreamResource resource = new StreamResource(imageName, inputStreamFactory);
        Image image = new Image(resource, "Food Image");
        image.setWidth("200px");
        image.setHeight("200px");
        return image;
    }

    private VerticalLayout createFoodLayout(Item item) {
        // Создаем компоненты для отображения названия, цены и изображения товара
        H3 itemName = new H3(item.getName());
        Paragraph itemPrice = new Paragraph("Price: $" + item.getPrice().toString());
        Image itemImage = createFoodImage(item.getImageName());

        // Создаем вертикальный макет и добавляем в него компоненты
        VerticalLayout foodLayout = new VerticalLayout(itemName, itemPrice, itemImage);
        foodLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        return foodLayout;
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
