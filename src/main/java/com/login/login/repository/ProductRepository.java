package com.login.login.repository;

import com.login.login.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
