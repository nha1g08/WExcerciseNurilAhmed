package com.worldpay.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto implements Serializable {
	
	  @Null
	  private Long id;
	  @NotBlank
	  private String description;
	  @Positive
	  private Long productId;
	  @NotBlank
	  private String currency;
	  @Positive
	  private Double price;
	  @Positive
	  private Long expiry;
	  
	  
	  

}
