package com.worldpay.util;

import com.worldpay.dto.OfferDto;
import com.worldpay.model.Offer;

public class OfferConverter {
	
	public static Offer toEntity(final OfferDto offerDto) {
	    return ModelMapperHolder.get().map(offerDto, Offer.class);
	  }
	
	public static OfferDto toDto(final Offer offer) {
	    return ModelMapperHolder.get().map(offer, OfferDto.class);
	  }

}
