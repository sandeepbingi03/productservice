package com.shoppingcart.org.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.org.entity.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
