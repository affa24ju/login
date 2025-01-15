package com.login.login.controller;

import com.login.login.model.Product;
import com.login.login.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("/product/{id}")
    public String productDetailPage(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetail";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }
    
    
}
