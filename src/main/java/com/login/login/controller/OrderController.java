package com.login.login.controller;

//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    
    //bara inloggade user med roll "user" kan kommat åt till orders
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders")
    public String orderPage(){
        return "orders";
    }
}
