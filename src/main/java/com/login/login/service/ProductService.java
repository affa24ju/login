package com.login.login.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.login.login.model.Product;
import com.login.login.repository.ProductRepository;



@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
        public List<Product> getAllProducts(){
            return productRepository.findAll();
    }

    public Product getProductById(int id){
        return productRepository.findById(id).orElse(null);
    }
    
}
