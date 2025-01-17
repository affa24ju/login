package com.login.login.controller;

import com.login.login.model.Product;
import com.login.login.service.ProductService;
import com.login.login.service.UserService;
import java.util.List;
import com.login.login.model.User;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private AuthenticationManager authenticationManager;

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
        model.addAttribute("isAuthenticated", isAuthenticated);
        return "index";

    }

    @GetMapping("/product/{id}")
    public String productDetailPage(@PathVariable("id") int id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("isAuthenticated", SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        return "productDetail";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        System.out.println("till register sidan");
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }
 
    @PostMapping("/login")
    public String handleLoginPage(@ModelAttribute("user") User user, Model model){
        System.out.println("till login page");
        try {
            // Försök logga in användaren med username och password
            UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Efter lyckad inloggning kan vi omdirigera till startsidan eller annan sida
            return "redirect:/";  // Omdirigera till startsidan
        } catch (Exception e) {
            // Om inloggningen misslyckas
            model.addAttribute("error", "Felaktigt användarnamn eller lösenord");
            return "login";  // Visa login-sidan igen med felmeddelande
        }
        
    } 
    
}
