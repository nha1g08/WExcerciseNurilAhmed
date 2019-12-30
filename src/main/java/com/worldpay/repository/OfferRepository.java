package com.worldpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldpay.model.Offer;

public interface OfferRepository  extends JpaRepository<Offer, Long>  {

}
