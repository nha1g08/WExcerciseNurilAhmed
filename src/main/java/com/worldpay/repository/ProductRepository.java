package com.worldpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldpay.model.Offer;
import com.worldpay.model.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>  {

}
