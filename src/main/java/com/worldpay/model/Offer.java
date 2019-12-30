package com.worldpay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "offer")
@NoArgsConstructor
@SequenceGenerator(name="seq", initialValue=1, allocationSize=10000)
public class Offer {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	  @Column(name = "id", updatable = false, nullable = false, unique = true)
	  private Long id;
	  
	  @Column(name = "description")
	  private String description;
	  
	  @ManyToOne
      @JoinColumn(name = "product_id", nullable = false)
      private Product product;
	  
	  @Column(name = "currency")
	  private String currency;
	  
	  @Column(name = "price")
	  private Double price;
	  
	  @Column(name = "expiry")
	  private Long expiry;

}
