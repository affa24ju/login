package com.login.login.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.login.login.model.User;
import com.login.login.service.UserService;


@Controller
public class UserController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        System.out.println("till register sidan");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //kryptera lösenord innan det sparas
        userService.saveUser(user); //spara användaren
        return "redirect:/";
    }

}
