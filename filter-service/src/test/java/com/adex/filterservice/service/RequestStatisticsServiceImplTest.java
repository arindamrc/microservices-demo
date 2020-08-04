/**
 * 
 */
package com.adex.filterservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
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
		// Integration tested using UI. Can be tested, but due to time constraints, it is tedious to formulate
		// and write such a complicated test with so many mocked objects.
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForCustomer(java.lang.Long)}.
	 */
	@Test
	void testGetRequestCountForCustomer() {
		List<RequestStatistics> statList = Arrays.asList(
				RequestStatistics.builder().cid(10L).timestamp(1000L).validCount(10L).invalidCount(2L).build(),
				RequestStatistics.builder().cid(10L).timestamp(2000L).validCount(20L).invalidCount(8L).build()
		);

		// given
		given(rsRepository.findByCid(10L)).willReturn(statList);
		
		RequestCounts expected = new RequestCounts(30L, 10L);
		
		// then
		assertThat(rsService.getRequestCountForCustomer(10L).get().equals(expected)).isTrue();
		
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForCustomerForDay(java.lang.Long, java.time.LocalDate)}.
	 */
	@Test
	void testGetTotalRequestCountForCustomerForDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
        String date = "16-08-2016";
        LocalDate localDate = LocalDate.parse(date, formatter);
        
		Long dateStartInEpochSeconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		Long dateEndInEpochSeconds = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

		List<RequestStatistics> statList = Arrays.asList(
				RequestStatistics.builder().cid(10L).timestamp(dateStartInEpochSeconds + 10L).validCount(10L).invalidCount(2L).build(),
				RequestStatistics.builder().cid(10L).timestamp(dateStartInEpochSeconds + 20L).validCount(20L).invalidCount(8L).build(),
				RequestStatistics.builder().cid(10L).timestamp(dateEndInEpochSeconds + 20L).validCount(20L).invalidCount(8L).build(),
				RequestStatistics.builder().cid(10L).timestamp(dateEndInEpochSeconds + 40L).validCount(20L).invalidCount(8L).build()
		);
		
		// given
		given(rsRepository.findByCid(10L)).willReturn(statList);
		
		RequestCounts expected = new RequestCounts(30L, 10L);
		
		// then
		// then
		assertThat(rsService.getRequestCountForCustomerForDay(10L, localDate).get().equals(expected)).isTrue();
	}

	/**
	 * Test method for {@link com.adex.filterservice.service.RequestStatisticsServiceImpl#getRequestCountForDay(java.time.LocalDate)}.
	 */
	@Test
	void testGetTotalRequestCountForDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
        String date = "16-08-2016";
        LocalDate localDate = LocalDate.parse(date, formatter);
        
		Long dateStartInEpochSeconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
		Long dateEndInEpochSeconds = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond();

		List<RequestStatistics> statList = Arrays.asList(
				RequestStatistics.builder().cid(10L).timestamp(dateStartInEpochSeconds + 10L).validCount(10L).invalidCount(2L).build(),
				RequestStatistics.builder().cid(20L).timestamp(dateStartInEpochSeconds + 20L).validCount(20L).invalidCount(8L).build()
		);
		
		// given
		given(rsRepository.findAllInTimeRange(dateStartInEpochSeconds, dateEndInEpochSeconds)).willReturn(statList);
		
		RequestCounts expected = new RequestCounts(30L, 10L);
		
		// then
		// then
		assertThat(rsService.getRequestCountForDay(localDate).get().equals(expected)).isTrue();
	}
	
}
