package com.login.login.service;

import com.login.login.model.User;
import com.login.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    //Spara användaren i databas
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName); // Returnera direkt Optional<User>
            
    }

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Försöker ladda användare: " + username);
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        System.out.println("user found: " + user.getUserName());
        System.out.println("password: " + user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), 
                user.getPassword(), 
                user.getAuthorities()); // använder getAuthorities() från User klassen
    }
    
}


