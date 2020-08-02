/**
 * 
 */
package com.adex.customerservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.adex.customerservice.domain.Customer;

/**
 * The database accessor for customer objects.
 * 
 * @author arc
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{

}
