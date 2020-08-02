/**
 * 
 */
package com.adex.customerservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.adex.customerservice.controller.CustomerController.BooleanResponse;
import com.adex.customerservice.domain.Customer;
import com.adex.customerservice.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for {@link CustomerController}.
 * 
 * @author arc
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@MockBean
	private CustomerService customerService;

	@Autowired
	private MockMvc mvc;

	private JacksonTester<Customer> customerJson;
	private JacksonTester<BooleanResponse> booleanResponseJson;

	/**
	 * The random customer id used for testing.
	 */
	private final Long testCid = 10L;
	private final String testCName = "testCustomer";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	/**
	 * Test method for {@link com.adex.customerservice.controller.CustomerController#getCustomer(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testGetCustomer() throws Exception {
		// given
		Customer saved = new Customer(testCName);
		saved.setId(testCid);

		given(customerService.getCustomer(testCid)).willReturn(saved);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/customer/get/"+testCid)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(customerJson.write(saved).getJson());
	}

	/**
	 * Test method for {@link com.adex.customerservice.controller.CustomerController#activateCustomer(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testActivateCustomer() throws Exception {
		// given
		Customer saved = new Customer(testCName);
		saved.setId(testCid);
		saved.setActive(true);
		
		BooleanResponse booleanResponse = new BooleanResponse(true);

		given(customerService.activateCustomer(testCid)).willReturn(saved);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/customer/activate/"+testCid)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(booleanResponse).getJson());
	}

	/**
	 * Test method for {@link com.adex.customerservice.controller.CustomerController#deactivateCustomer(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testDeactivateCustomer() throws Exception {
		// given
		Customer saved = new Customer(testCName);
		saved.setId(testCid);
		saved.setActive(false);
		
		BooleanResponse booleanResponse = new BooleanResponse(true);

		given(customerService.activateCustomer(testCid)).willReturn(saved);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/customer/deactivate/"+testCid)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(booleanResponse).getJson());
	}

	/**
	 * Test method for {@link com.adex.customerservice.controller.CustomerController#createCustomer(com.adex.customerservice.domain.Customer)}.
	 */
	@Test
	void testCreateCustomer() throws Exception {
		// given
		Customer customer = new Customer(testCName);
		
		Customer saved = new Customer(testCName);
		saved.setId(testCid);
		saved.setActive(true);
		
		given(customerService.createCustomer(customer)).willReturn(saved);

		// when
		MockHttpServletResponse response = mvc.perform(
				post("/customer/create/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(customerJson.write(customer).getJson())
				).andReturn().getResponse();
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(customerJson.write(saved).getJson());
	}

	/**
	 * Test method for {@link com.adex.customerservice.controller.CustomerController#deleteCustomer(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testDeleteCustomer() throws Exception {
		// given
		BooleanResponse booleanResponse = new BooleanResponse(true);

		given(customerService.deleteCustomer(testCid)).willReturn(true);

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/customer/delete/"+testCid)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(booleanResponseJson.write(booleanResponse).getJson());
	}

}
