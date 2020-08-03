/**
 * 
 */
package com.adex.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.exceptions.CustomerNotFoundException;
import com.adex.customerservice.service.CustomerService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author arc
 *
 */
@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

	private final CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	/**
	 * Get a customer by cid.
	 * 
	 * @param cid
	 * @return Customer
	 */
	@GetMapping("/get/{cid}")
	public ResponseEntity<Customer> getCustomer(final @PathVariable("cid") Long cid) {
		Customer customer = null;
		try {
			customer = customerService.getCustomer(cid);
		} catch (CustomerNotFoundException e) {
			log.error("Retrieving customer threw error {}", e.getMessage());
			return ResponseEntity.notFound().build();		
		}
		return ResponseEntity.ok().body(customer);
	}
	
	
	/**
	 * Set customer with id {@code cid} active.
	 * Calling this operation more than once will
	 * <b>not</b> result in error.
	 * 
	 * @param cid
	 * @return activated customer.
	 */
	@GetMapping("/activate/{cid}")
	@SuppressWarnings("unused")
	public ResponseEntity<BooleanResponse> activateCustomer(final @PathVariable("cid") Long cid) {
		BooleanResponse response = new BooleanResponse(true);
		try {
			Customer activated = customerService.activateCustomer(cid);
		} catch (CustomerNotFoundException e) {
			log.error("Activating customer threw error {}", e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	/**
	 * Set customer with id {@code cid} inactive.
	 * Calling this operation more than once will
	 * <b>not</b> result in error.
	 * 
	 * @param cid
	 * @return deactivated customer.
	 */
	@GetMapping("/deactivate/{cid}")
	@SuppressWarnings("unused")
	public ResponseEntity<BooleanResponse> deactivateCustomer(final @PathVariable("cid") Long cid) {
		BooleanResponse response = new BooleanResponse(true);
		try {
			Customer deactivated = customerService.deactivateCustomer(cid);
		} catch (CustomerNotFoundException e) {
			log.error("Deactivating customer threw error {}", e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(response);
	}
	
	
	/**
	 * Create a new customer.
	 * 
	 * @param customer
	 * @return a boolean response
	 */
	@PostMapping("/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer saved = null;
		try {
			saved = customerService.createCustomer(customer);
		} catch (Exception e) {
			log.error("Retrieving customer threw error {}", e.getMessage());
			return ResponseEntity.unprocessableEntity().build();	
		}
		return ResponseEntity.ok().body(saved);
	}
	
	
	/**
	 * Deletes customer with id {@code cid} from database.
	 * 
	 * @param cid
	 * @return
	 */
	@GetMapping("/delete/{cid}")
	public ResponseEntity<BooleanResponse> deleteCustomer(final @PathVariable("cid") Long cid) {
		BooleanResponse response = new BooleanResponse(true);
		try {
			response.setResponse(customerService.deleteCustomer(cid));
		} catch (CustomerNotFoundException e) {
			log.error("Deleting customer threw error {}", e.getMessage());
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(response);
	}
	
	@AllArgsConstructor
	@Getter
	@Setter
	static final class BooleanResponse {
		private boolean response;
	}
}
