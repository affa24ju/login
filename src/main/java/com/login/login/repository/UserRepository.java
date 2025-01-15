package com.login.login.repository;

import org.springframework.data.repository.CrudRepository;

import com.login.login.model.User;



public interface UserRepository extends CrudRepository<User, Integer> {

    
}