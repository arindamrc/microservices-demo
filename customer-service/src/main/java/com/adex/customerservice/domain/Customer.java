/**
 * 
 */
package com.adex.customerservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * This class represents a customer.
 * 
 * @author arc
 *
 */
/**
 * @author arc
 *
 */
@Data
@Entity
@AllArgsConstructor
@Table(name = "CUSTOMERS")
public final class Customer {
	
	
	/**
	 * The customer id.
	 */
	@Id
	@GeneratedValue
	@Column(name="CUSTOMER_ID", length = 11)
	private Long id;
	
	/**
	 * The customer name.
	 */
	@NonNull
	@Column(nullable = false, length = 255, name = "CUSTOMER_NAME")
	@Size(min=1, max=255)
	private String name;
	
	/**
	 * If the customer is active.
	 * Set to true by default so that 
	 * a newly added customer is active
	 * by default.
	 */
	@Column(name = "CUSTOMER_STATUS")
	private boolean active = true;
	
	
	/**
	 * Needed for JSON/JPA
	 */
	Customer(){
		this(0L, "", true);
	}
	

	/**
	 * Create a customer with the given name.
	 * Customer is active by default.
	 * 
	 * @param name
	 */
	public Customer(String name) {
		this.name = name;
		this.active = true;
	}

}
