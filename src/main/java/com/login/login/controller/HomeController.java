package com.login.login.controller;

import com.login.login.model.Product;
import com.login.login.service.ProductService;
//import com.login.login.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    /*@Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;*/

    @GetMapping("/")
    public String homePage(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        //har använt direkt men inte funkat, därför använder boolean
        boolean isAuthenticated = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
            !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
        }
        System.out.println("Is authenticated: " + isAuthenticated);
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "index";

    }

    @GetMapping("/product/{id}")
    public String productDetailPage(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        
        boolean isAuthenticated = false;
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
            !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            isAuthenticated = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        //model.addAttribute("isAuthenticated", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return "productDetail";
    }
    
}
