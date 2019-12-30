package com.worldpay.util;

import com.worldpay.service.OfferService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpireTask implements Runnable {
	

	private final OfferService offerService;
	
	private final Long id;
	
	@Override
	public void run() {
		offerService.deleteById(id);
	}
	
}
