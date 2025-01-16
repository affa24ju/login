package com.login.login.controller;

import com.login.login.model.Product;
import com.login.login.service.ProductService;
import com.login.login.service.UserService;
import java.util.List;
import com.login.login.model.User;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage(Model model){
        List<Product> products = productService.getAllProducts();
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
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }
    
}
