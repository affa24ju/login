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

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUserName(String userName) {
        return Optional.ofNullable(userRepository.findByUserName(userName));
    }

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
            .map(user -> new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), 
                    user.getAuthorities()))  // Här används getAuthorities()
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}


