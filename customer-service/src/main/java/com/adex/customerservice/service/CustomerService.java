/**
 * 
 */
package com.adex.customerservice.service;

import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.exceptions.CustomerNotFoundException;

/**
 * Service class for customers.
 * 
 * @author arc
 *
 */
public interface CustomerService {
	
	
	/**
	 * Add a new customer.
	 * 
	 * @param customer
	 * @return {@code true} if operation successful, false otherwise.
	 */
	public Customer createCustomer(Customer customer);
	
	
	/**
	 * Deactivate customer with the given customer id.
	 * 
	 * @param cid : customer id
	 * @return {@code true} if operation successful, false otherwise.
	 * @throws CustomerNotFoundException in case {@code cid} doesn't exist.
	 */
	public Customer deactivateCustomer(Long cid) throws CustomerNotFoundException;
	
	
	/**
	 * Activate customer with the given customer id.
	 * 
	 * @param cid : customer id
	 * @return {@code true} if operation successful, false otherwise.
	 * @throws CustomerNotFoundException in case {@code cid} doesn't exist.
	 */
	public Customer activateCustomer(Long cid) throws CustomerNotFoundException;
	
	
	/**
	 * Delete the customer with the given customer id.
	 * 
	 * @param cid : customer id
	 * @return {@code true} if operation successful, false otherwise.
	 * @throws CustomerNotFoundException in case {@code cid} doesn't exist.
	 */
	public boolean deleteCustomer(Long cid) throws CustomerNotFoundException;
	
	/**
	 * Get the customer details corresponding to the given customer id.
	 * 
	 * @param cid
	 * @return
	 * @throws CustomerNotFoundException in case {@code cid} doesn't exist.
	 */
	public Customer getCustomer(Long cid) throws CustomerNotFoundException;

}
