/**
 * 
 */
package com.adex.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.event.CustomerDeletionEvent;
import com.adex.customerservice.event.EventDispatcher;
import com.adex.customerservice.exceptions.CustomerNotFoundException;
import com.adex.customerservice.repository.CustomerRepository;

/**
 * @author arc
 *
 */
public class CustomerServiceImplTest {

	
	/**
	 * Object under test.
	 */
	private CustomerServiceImpl customerServiceImpl;
	
	/**
	 * The random customer id used in the tests. 
	 */
	private Long testCid = 10L;
	
	/**
	 * Mocked repository object as we don't want to change the database
	 * during tests.
	 */
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private EventDispatcher eventDispatcher;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customerServiceImpl = new CustomerServiceImpl(customerRepository, eventDispatcher);
	}

	/**
	 * Test method for {@link com.adex.customerservice.service.CustomerServiceImpl#createCustomer(com.adex.customerservice.domain.Customer)}.
	 */
	@Test
	public void testCreateCustomer() {
		// given 
		Customer newCustomer = new Customer("testcustomer");
		Customer createdCustomer = new Customer("testCustomer");
		createdCustomer.setId(testCid); // set any random id.
		
		given(customerRepository.save(newCustomer)).willReturn(createdCustomer);
		
		// when 
		Customer saved = customerServiceImpl.createCustomer(newCustomer);
		
		// then
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(0);
		verify(customerRepository).save(newCustomer);
	}

	/**
	 * Test method for {@link com.adex.customerservice.service.CustomerServiceImpl#deactivateCustomer(java.lang.Long)}.
	 */
	@Test
	public void testDeactivateCustomer() {
		// given 
		Customer createdCustomer = new Customer("testCustomer");
		createdCustomer.setId(testCid); // set any random id.
		
		given(customerRepository.findById(testCid)).willReturn(Optional.of(createdCustomer));
		
		createdCustomer.setActive(false);
		given(customerRepository.save(createdCustomer)).willReturn(createdCustomer);
		
		// when 
		Customer saved = null;
		try {
			saved = customerServiceImpl.deactivateCustomer(testCid);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected error during update!");
		}
		
		assertThat(saved).isNotNull();
		assertThat(saved.isActive()).isFalse();
	}

	/**
	 * Test method for {@link com.adex.customerservice.service.CustomerServiceImpl#activateCustomer(java.lang.Long)}.
	 */
	@Test
	public void testActivateCustomer() {
		// given 
		Customer createdCustomer = new Customer("testCustomer");
		createdCustomer.setId(testCid); // set any random id.
		createdCustomer.setActive(false);
		
		given(customerRepository.findById(10L)).willReturn(Optional.of(createdCustomer));
		
		createdCustomer.setActive(true);
		given(customerRepository.save(createdCustomer)).willReturn(createdCustomer);
		
		// when 
		Customer saved = null;
		try {
			saved = customerServiceImpl.activateCustomer(testCid);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected error during update!");
		}
		
		assertThat(saved).isNotNull();
		assertThat(saved.isActive()).isTrue();		
	}

	/**
	 * Test method for {@link com.adex.customerservice.service.CustomerServiceImpl#deleteCustomer(java.lang.Long)}.
	 */
	@Test
	public void testDeleteCustomer() {
		// given 
		Customer createdCustomer = new Customer("testCustomer");
		createdCustomer.setId(testCid); // set any random id.
		
		given(customerRepository.findById(testCid)).willReturn(Optional.of(createdCustomer));
		
		// when 
		boolean retval = false;
		try {
			retval = customerServiceImpl.deleteCustomer(testCid);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected error during update!");
		}
		
		// then
		assertThat(retval).isTrue();
		verify(customerRepository).delete(createdCustomer);
		verify(eventDispatcher).sendCustomerDeletionEvent(eq(new CustomerDeletionEvent(testCid)));
	}
	
	
	/**
	 * Test method for {@link com.adex.customerservice.service.CustomerServiceImpl#getCustomer(java.lang.Long)}.
	 */
	@Test
	public void testGetCustomer() {
		// given 
		Customer createdCustomer = new Customer("testCustomer");
		createdCustomer.setId(testCid); // set any random id.
		createdCustomer.setActive(true);
		
		given(customerRepository.findById(testCid)).willReturn(Optional.of(createdCustomer));
		
		// when 
		Customer saved = null;
		try {
			saved = customerServiceImpl.getCustomer(testCid);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected error during update!");
		}
		
		assertThat(saved).isNotNull();
		assertThat(saved.isActive()).isTrue();		
	}

}
