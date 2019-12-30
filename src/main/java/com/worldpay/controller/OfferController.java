package com.worldpay.controller;

import static com.worldpay.util.OfferConverter.toEntity;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldpay.dto.OfferDto;
import com.worldpay.model.Offer;
import com.worldpay.service.OfferService;
import com.worldpay.util.ExpireTask;
import com.worldpay.util.OfferConverter;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/offers", produces = "application/json")
public class OfferController {

	private final OfferService offerService;
	
	private final ScheduledExecutorService offerExpireExecutor = Executors.newScheduledThreadPool(10);

	@GetMapping("/{id}")
	public ResponseEntity getOfferById(@PathVariable Long id) {
		Optional<Offer> offerOptional = offerService.findById(id);
		if(!offerOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("offer id: "+id+" not found");
		}
		else {
			Offer offer = offerOptional.get();
			return ResponseEntity.status(HttpStatus.OK).body(OfferConverter.toDto(offer));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteOfferById(@PathVariable Long id) {
		try {
			offerService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("offer id: "+id+" deleted");
		}catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("offer id: "+id+" not found");	
		}
		
	}
	
	@PostMapping
	public ResponseEntity createOffer(@Valid @RequestBody OfferDto offerDto) {
		Optional<Offer> offerOptional = offerService.create(toEntity(offerDto));
		if (!offerOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Product does not exist");
		} else {
			Offer offer = offerOptional.get();
			offerDto.setId(offer.getId());
			offerExpireExecutor.schedule(new ExpireTask(offerService, offer.getId()), offer.getExpiry(), TimeUnit.MILLISECONDS);
			return ResponseEntity.status(HttpStatus.CREATED).body(offerDto);
		}
	}

}
