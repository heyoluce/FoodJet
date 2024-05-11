package com.project.foodjet.config;

import com.project.foodjet.ui.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Customize your WebSecurity configuration.
        super.configure(web);
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("customer")
                // password = password with this hash, don't tell anybody :-)
                .password("{noop}password")
                .roles("CUSTOMER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();
        UserDetails adminTwo = User.builder()
                .username("Aman")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        UserDetails courier = User.builder()
                .username("courier")
                .password("{noop}password")
                .roles("COURIER")
                .build();
        return new InMemoryUserDetailsManager(user, admin, adminTwo, courier);
    }

}
