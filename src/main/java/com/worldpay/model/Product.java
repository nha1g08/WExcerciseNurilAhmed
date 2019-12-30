package com.worldpay.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	  @Id
	  @GeneratedValue
	  @Column(name = "id", updatable = false, nullable = false, unique = true)
	  private Long id;
	  
	  @Column(name = "description")
	  private String description;
	  
	  @OneToMany(
		        mappedBy = "product",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true
		    )
      private List<Offer> offers = new ArrayList<>();
}
