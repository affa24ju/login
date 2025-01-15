package com.login.login.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.login.login.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
}