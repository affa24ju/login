package com.login.login.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    
    @Secured("ROLE_USER")
    @GetMapping("/orders")
    public String orderPage(){
        return "orders";
    }
}
