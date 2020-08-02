/**
 * 
 */
package com.adex.customerservice.exceptions;

import com.adex.customerservice.domain.Customer;

/**
 * An exception that's thrown when a customer is not found.
 * 
 * @author arc
 *
 */
public class CustomerNotFoundException extends Exception {

	private static final long serialVersionUID = -5792040019171993408L;
	
	private String message;
	
	public CustomerNotFoundException(Long cid) {
		this.message = "Customer with ID: " + cid + "not found !";
	}
	
	@Override
	public String getMessage() {
		return message;
	}

}
