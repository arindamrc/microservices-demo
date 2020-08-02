/**
 * 
 */
package com.adex.filterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a customer.
 * 
 * @author arc
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class Customer {
	
	
	/**
	 * The customer id.
	 */
	private Long id;
	
	/**
	 * The customer name.
	 */
	private String name;
	
	/**
	 * Whether the customer is active.
	 */
	private boolean active;
	
}
