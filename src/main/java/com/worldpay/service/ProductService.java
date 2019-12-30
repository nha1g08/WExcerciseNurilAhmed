package com.worldpay.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldpay.model.Product;
import com.worldpay.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	
	public Optional<Product> findById(long id) {
	    return productRepository.findById(id);
	  }

	public Optional<Product> create(Product product) {
		Product savedProduct = productRepository.save(product);
		return Optional.of(savedProduct);
	}

}
