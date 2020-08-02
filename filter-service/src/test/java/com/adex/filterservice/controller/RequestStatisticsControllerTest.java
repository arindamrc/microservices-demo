/**
 * 
 */
package com.adex.filterservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
import com.adex.filterservice.dto.Request;
import com.adex.filterservice.service.RequestStatisticsService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author arc
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RequestStatisticsController.class)
class RequestStatisticsControllerTest {
	
	
	@MockBean
	private RequestStatisticsService rss;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<RequestStatistics> requestStatisticsJson;
	
	private JacksonTester<RequestCounts> requestCountsJson;
	
	private JacksonTester<Request> requestJson;
	
	private Long testCid = 10L;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.RequestStatisticsController#getRequestCountForCustomer(java.lang.Long)}.
	 */
	@Test
	void testGetRequestCountForCustomer() throws Exception {
		RequestCounts counts = new RequestCounts(100L, 2L);
		
		// given
		given(rss.getRequestCountForCustomer(testCid)).willReturn(Optional.of(counts));

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/stats/customer/"+testCid)
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(requestCountsJson.write(counts).getJson());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.RequestStatisticsController#getRequestCountForCustomerForDay(java.lang.Long, java.time.LocalDate)}.
	 * @throws Exception 
	 */
	@Test
	void testGetRequestCountForCustomerForDay() throws Exception {
		RequestCounts counts = new RequestCounts(100L, 2L);
		LocalDate today = LocalDate.now();
		
		// given
		given(rss.getRequestCountForCustomerForDay(testCid, today)).willReturn(Optional.of(counts));

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/stats/customer-day/"+testCid+"/"+today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(requestCountsJson.write(counts).getJson());
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.RequestStatisticsController#getRequestCountsForDay(java.time.LocalDate)}.
	 */
	@Test
	void testGetRequestCountsForDay() throws Exception {
		RequestCounts counts = new RequestCounts(100L, 2L);
		LocalDate today = LocalDate.now();
		
		// given
		given(rss.getRequestCountForDay(today)).willReturn(Optional.of(counts));

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/stats/day/"+today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
				.accept(MediaType.APPLICATION_JSON)
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(requestCountsJson.write(counts).getJson());		
	}

	/**
	 * Test method for {@link com.adex.filterservice.controller.RequestStatisticsController#logRequest(com.adex.filterservice.dto.Request)}.
	 */
	@Test
	void testLogRequest() throws Exception {
		// given
		RequestStatistics stats = RequestStatistics.builder().id(5L).cid(10L).invalidCount(3L).validCount(100L).timestamp(1010L).build();
		Request request = Request.builder().cid(10L).ip("192.168.1.1").timestamp(1010L).tid(2).uid("Mozilla").build();
		
		given(rss.addRequest(request)).willReturn(stats);
		
		// when
		MockHttpServletResponse response = mvc.perform(
				post("/stats/request/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson.write(request).getJson())
				).andReturn().getResponse();
		
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(requestStatisticsJson.write(stats).getJson());	
				
	}

}
