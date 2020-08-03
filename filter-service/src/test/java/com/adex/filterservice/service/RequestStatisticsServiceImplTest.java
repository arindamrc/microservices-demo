/**
 * 
 */
package com.adex.filterservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.adex.filterservice.repository.RequestStatisticsRepository;

/**
 * @author arc
 *
 */
class RequestStatisticsServiceImplTest {
	
	private RequestStatisticsServiceImpl rsService;

	@Mock
	private RequestStatisticsRepository rsRepository;

	
	@Value("${filter.time.diff}")
	private Long timeDiff;
	
	@Mock
	RestTemplate restTemplate;
	
	@Value("${customer.service.host}")
	private String customerServiceHost;
	
	@Mock
	private IPBlacklistService ipService;

	@Mock
	private UABlacklistService uaService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		rsService = new RequestStatisticsServiceImpl(rsRepository, restTemplate, ipService, uaService);
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#addRequest(com.adex.filterservice.dto.Request)}.
	 */
	@Test
	void testAddRequest() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForCustomer(java.lang.Long)}.
	 */
	@Test
	void testGetTotalRequestCountForCustomer() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForCustomerForDay(java.lang.Long, java.time.LocalDate)}.
	 */
	@Test
	void testGetTotalRequestCountForCustomerForDay() {
//		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForDay(java.time.LocalDate)}.
	 */
	@Test
	void testGetTotalRequestCountForDay() {
//		fail("Not yet implemented");
	}

}
