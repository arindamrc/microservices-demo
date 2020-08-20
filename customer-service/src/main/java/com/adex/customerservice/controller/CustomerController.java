/**
 * 
 */
package com.adex.customerservice.controller;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.exceptions.CustomerNotFoundException;
import com.adex.customerservice.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
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
@Produces("application/json")
@Api(value = "customer")
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
	@ApiOperation(
		value = "Get customer by Id.",
	    notes = "A unique customer identified by the Id is returned.",
	    response = Customer.class)
	@ApiResponse(code = 404, message = "Customer not found")
	public ResponseEntity<Customer> getCustomer(
			@ApiParam("Customer Id") final @PathVariable("cid") Long cid) {
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
	@PutMapping("/activate/{cid}")
	@ApiOperation(
			value = "Activate customer by Id.",
		    notes = "A unique customer is activated by the Id supplied.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 404, message = "Customer not found")
	@SuppressWarnings("unused")
	public ResponseEntity<BooleanResponse> activateCustomer(
			@ApiParam("Customer Id") final @PathVariable("cid") Long cid) {
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
	@PutMapping("/deactivate/{cid}")
	@ApiOperation(
			value = "Deactivate customer by Id.",
		    notes = "A unique customer is de-activated by the Id supplied.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 404, message = "Customer not found")
	@SuppressWarnings("unused")
	public ResponseEntity<BooleanResponse> deactivateCustomer(
			@ApiParam("Customer Id") final @PathVariable("cid") Long cid) {
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
	@ApiOperation(
			value = "Create a new customer.",
		    notes = "A new customer is created with the supplied data.",
		    response = Customer.class)
	@ApiResponse(code = 422, message = "Cannot process the supplied data.")
	public ResponseEntity<Customer> createCustomer(
			@ApiParam("Customer Data") @RequestBody Customer customer) {
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
	@DeleteMapping("/delete/{cid}")
	@ApiOperation(
			value = "Delete customer by Id.",
		    notes = "A unique customer is deleted by the Id supplied.",
		    response = BooleanResponse.class)
	@ApiResponse(code = 404, message = "Customer not found")
	public ResponseEntity<BooleanResponse> deleteCustomer(
			@ApiParam("Customer Id") final @PathVariable("cid") Long cid) {
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
