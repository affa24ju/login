package com.login.login.controller;

import com.login.login.model.Product;
import com.login.login.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String homePage(Model model){
        List<Product> products = ProductService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("isAuthenticated", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return "index";

    }
    
    
}
