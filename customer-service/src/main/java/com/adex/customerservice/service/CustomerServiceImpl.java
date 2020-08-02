package com.adex.customerservice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.event.CustomerDeletionEvent;
import com.adex.customerservice.event.EventDispatcher;
import com.adex.customerservice.exceptions.CustomerNotFoundException;
import com.adex.customerservice.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * @author arc
 *
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final EventDispatcher eventDispatcher;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, EventDispatcher eventDispatcher) {
		this.customerRepository = customerRepository;
		this.eventDispatcher = eventDispatcher;
	}

	@Override
	@Transactional
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	@Transactional
	public Customer deactivateCustomer(Long cid) throws CustomerNotFoundException {
		Optional<Customer> customerOptional = customerRepository.findById(cid);
		
		if (!customerOptional.isPresent()) {
			CustomerNotFoundException cnfe = new CustomerNotFoundException(cid);
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		
		Customer customer = customerOptional.get();
		customer.setActive(false);
		Customer saved = customerRepository.save(customer);
		
		Assert.isTrue(customer.getId() == saved.getId(), "Updating customer failed; new one created instead!");
		return saved;
	}

	@Override
	@Transactional
	public Customer activateCustomer(Long cid) throws CustomerNotFoundException {
		Optional<Customer> customerOptional = customerRepository.findById(cid);
		
		if (!customerOptional.isPresent()) {
			CustomerNotFoundException cnfe = new CustomerNotFoundException(cid);
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		
		Customer customer = customerOptional.get();
		customer.setActive(true);
		Customer saved = customerRepository.save(customer);
		
		Assert.isTrue(customer.getId() == saved.getId(), "Updating customer failed; new one created instead!");
		return saved;
	}

	@Override
	@Transactional
	public boolean deleteCustomer(Long cid) throws CustomerNotFoundException {
		boolean retval = true;
		
		Optional<Customer> customerOptional = customerRepository.findById(cid);
		
		if (!customerOptional.isPresent()) {
			CustomerNotFoundException cnfe = new CustomerNotFoundException(cid);
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		
		Customer customer = customerOptional.get();
		
		try {
			customerRepository.delete(customer);
		} catch (Exception e) {
			retval = false;
			log.error(e.getMessage());
		}
		
		eventDispatcher.sendCustomerDeletionEvent(new CustomerDeletionEvent(cid));
		
		return retval;
		
	}
	
	@Override
	public Customer getCustomer(Long cid) throws CustomerNotFoundException {
		Optional<Customer> customerOptional = customerRepository.findById(cid);
	
		if (!customerOptional.isPresent()) {
			CustomerNotFoundException cnfe = new CustomerNotFoundException(cid);
			log.error(cnfe.getMessage());
			throw cnfe;
		}
		
		return customerOptional.get();
	}
	

}
