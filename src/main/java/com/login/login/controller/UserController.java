package com.login.login.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.login.login.model.User;
import com.login.login.repository.UserRepository;
import com.login.login.service.UserService;
//import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    
    private final UserService userService;
    //private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //@Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        //this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*@GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public User findById(@PathVariable("id") User user){
        return user;
    }*/

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
