package com.worldpay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worldpay.model.Offer;
import com.worldpay.model.Product;
import com.worldpay.repository.OfferRepository;
import com.worldpay.util.ExpireTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OfferService {

	private final OfferRepository offerRepository;
	
	@Autowired
	ProductService productService;
	
	public Optional<Offer> findById(long id) {
	    log.info("get offer id: "+id);
	    return offerRepository.findById(id);
	  }
	
	public void deleteById(long id) {
		log.info("delete offer id: "+id);
	    offerRepository.deleteById(id);
	  }

	public Optional<Offer> create(Offer offer) {
		log.info("post offer id: "+offer.getDescription());
		Optional<Product> productOptional = productService.findById(offer.getProduct().getId());
		if(!productOptional.isPresent()) {
			return Optional.empty();
		}
		Offer savedOffer = offerRepository.save(offer);
		return Optional.of(savedOffer);
	}

}
