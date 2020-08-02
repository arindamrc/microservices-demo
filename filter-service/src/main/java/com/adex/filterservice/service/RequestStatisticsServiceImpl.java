/**
 * 
 */
package com.adex.filterservice.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
import com.adex.filterservice.dto.Customer;
import com.adex.filterservice.dto.Request;
import com.adex.filterservice.exceptions.CustomerInactiveException;
import com.adex.filterservice.exceptions.CustomerNotFoundException;
import com.adex.filterservice.exceptions.IPBlacklistedException;
import com.adex.filterservice.exceptions.IllFormedIPException;
import com.adex.filterservice.exceptions.UABlacklistedException;
import com.adex.filterservice.repository.RequestStatisticsRepository;

import inet.ipaddr.AddressStringException;
import inet.ipaddr.IncompatibleAddressException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link RequestStatisticsService}.
 * 
 * @author arc
 *
 */
@Service
@Slf4j
public class RequestStatisticsServiceImpl implements RequestStatisticsService {
	
	private final RequestStatisticsRepository rsr;
	
	private final Long timeDiff;
	
	private final RestTemplate restTemplate;
	
	private final String customerServiceHost;
	
	private final IPBlacklistService ipService;
	
	private final UABlacklistService uaService;
	
	@Autowired
	public RequestStatisticsServiceImpl(
			RequestStatisticsRepository requestStatisticsRepository, 
			@Value("${filter.time.diff}") Long timeDiff,
			RestTemplate restTemplate,
			@Value("customer.service.host") String customerServiceHost,
			IPBlacklistService ipService,
			UABlacklistService uaService) {
		this.rsr = requestStatisticsRepository;
		this.timeDiff = timeDiff;
		this.restTemplate = restTemplate;
		this.customerServiceHost = customerServiceHost;
		this.ipService = ipService;
		this.uaService = uaService;
	}

	@Override
	@Transactional
	public RequestStatistics addRequest(Request request) throws CustomerNotFoundException, Exception {
		Long invalidCount = 0L;
		
		try {
			validateRequest(request);
		} catch (CustomerNotFoundException e) {
			log.error("Customer with ID: {} not found", request.getCid());
			throw e;
		} catch (CustomerInactiveException e) {
			log.error("Customer with ID: {} inactive", request.getCid());
			invalidCount++;
		} catch (IllFormedIPException e) {
			log.error("IP: {} is ill formed", request.getIp());
			invalidCount++;
		} catch (IPBlacklistedException e) {
			log.error("IP: {} is blacklisted", request.getIp());
			invalidCount++;
		} catch (UABlacklistedException e) {
			log.error("UA: {} is blacklisted", request.getUid());
			invalidCount++;
		} catch (Exception e) {
			log.error("Unexpected runtime error", e);
			throw e;
		}
		
		Optional<RequestStatistics> statOptional = rsr.findLatestStatistic(request.getCid());
		
		// this is the first time the customer is making the request
		if (!statOptional.isPresent()) {
			RequestStatistics.RequestStatisticsBuilder builder = RequestStatistics.builder()
				.cid(request.getCid())
				.timestamp(request.getTimestamp()
			);
			
			if (invalidCount > 0L) {
				builder.invalidCount(invalidCount);
			}else {
				builder.validCount(1L);
			}
			
			RequestStatistics saved = rsr.save(builder.build());
			
			return saved;
		}
		
		
		Assert.isTrue(statOptional.get().getTimestamp() > request.getTimestamp(), "Stale Timestamp in request!");
		
		// We have assumed that the time-stamps are in seconds from epoch.
		// Check if the last time-stamp exceeds the current one by more than the configured time difference.
		Long lastTS = statOptional.get().getTimestamp();
		Long curTS = request.getTimestamp();
		if ((curTS - lastTS) > timeDiff) {
			// the time difference has passed; add new row to database
			RequestStatistics.RequestStatisticsBuilder builder = RequestStatistics.builder()
					.cid(request.getCid())
					.timestamp(request.getTimestamp()
			);
			
			if (invalidCount > 0L) {
				builder.invalidCount(invalidCount);
			}else {
				builder.validCount(1L);
			}
			
			RequestStatistics saved = rsr.save(builder.build());
			
			return saved;
		}
		
		// We are still within the time window. Increment the request counters.
		RequestStatistics.RequestStatisticsBuilder builder = RequestStatistics.builder()
				.cid(statOptional.get().getCid())
				.timestamp(statOptional.get().getTimestamp()
		);
		
		if (invalidCount > 0L) {
			builder.invalidCount(statOptional.get().getInvalidCount() + invalidCount);
		}else {
			builder.validCount(statOptional.get().getValidCount() + 1L);
		}
		
		RequestStatistics saved = rsr.save(builder.build());
		return saved;
	}

	@Override
	public Optional<RequestCounts> getRequestCountForCustomer(Long cid) {
		List<RequestStatistics> stats = rsr.findByCid(cid);
		
		if (stats.isEmpty()) {
			return Optional.empty();
		}
		
		Long validCount = stats.stream().mapToLong(x -> x.getValidCount()).sum();
		Long invalidCount = stats.stream().mapToLong(x -> x.getInvalidCount()).sum();
		
		return Optional.of(new RequestCounts(validCount, invalidCount));
	}

	@Override
	public Optional<RequestCounts> getRequestCountForCustomerForDay(Long cid, LocalDate date) {
		OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
		ZoneOffset zoneOffset = odt.getOffset();
		
		Long dateStartInEpochSeconds = date.toEpochSecond(LocalTime.MIN, zoneOffset);
		Long dateEndInEpochSeconds = date.toEpochSecond(LocalTime.MAX, zoneOffset);
		
		List<RequestStatistics> stats = rsr.findByCid(cid);
		
		if (stats.isEmpty()) {
			return Optional.empty();
		}
		
		Long validCount = stats
				.stream()
				.filter(x -> x.getTimestamp() > dateStartInEpochSeconds && x.getTimestamp() < dateEndInEpochSeconds)
				.mapToLong(x -> x.getValidCount())
				.sum();
		
		Long invalidCount = stats
				.stream()
				.filter(x -> x.getTimestamp() > dateStartInEpochSeconds && x.getTimestamp() < dateEndInEpochSeconds)
				.mapToLong(x -> x.getInvalidCount())
				.sum();
		
		return Optional.of(new RequestCounts(validCount, invalidCount));
	}

	@Override
	public Optional<RequestCounts> getRequestCountForDay(LocalDate date) {
		OffsetDateTime odt = OffsetDateTime.now(ZoneId.systemDefault());
		ZoneOffset zoneOffset = odt.getOffset();
		
		Long dateStartInEpochSeconds = date.toEpochSecond(LocalTime.MIN, zoneOffset);
		Long dateEndInEpochSeconds = date.toEpochSecond(LocalTime.MAX, zoneOffset);
		
		List<RequestStatistics> stats = rsr.findAllInTimeRange(dateStartInEpochSeconds, dateEndInEpochSeconds);
		
		if (stats.isEmpty()) {
			return Optional.empty();
		}
		
		Long validCount = stats.stream().mapToLong(x -> x.getValidCount()).sum();
		Long invalidCount = stats.stream().mapToLong(x -> x.getInvalidCount()).sum();
		
		return Optional.of(new RequestCounts(validCount, invalidCount));
	}
	
	/**
	 * Validate the request. The following validations are made:
	 * 1. Check if the customer id exists with the customer service.
	 * 2. Check if the customer is active.
	 * 3. Check if the IP address is blacklisted.
	 * 4. Check if the user agent is blacklisted.
	 * 
	 * @param request
	 * @return
	 */
	private void validateRequest(Request request) throws CustomerNotFoundException, CustomerInactiveException, IPBlacklistedException, UABlacklistedException, IllFormedIPException, Exception {
		Customer customer = null;
		
		// check if the customer exists
        try {
			ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(
			    customerServiceHost + "/customer/get/" + request.getCid(),
			    Customer.class
			);
			
			if (customerResponse.getStatusCode() == HttpStatus.OK) {
				customer = customerResponse.getBody();
			} else {
				throw new CustomerNotFoundException();
			}
		} catch (RestClientException e) {
			// Something went wrong. Assume that customer doen't exist.
			log.error("Error retrieving customer!", e);
			throw new CustomerNotFoundException();
		}
        // customer exists
        
        // check if customer is enabled
        if (!customer.isActive()) {
        	throw new CustomerInactiveException();
		}
        
        // check if the requesting IP address is blacklisted
        try {
			if (ipService.isBlacklisted(request.getIp())) {
				throw new IPBlacklistedException();
			}
		} catch (AddressStringException | IncompatibleAddressException e) {
			throw new IllFormedIPException();
		}
        
        // check if the requesting user agent is blacklisted
        if (uaService.isBlacklisted(request.getUid())) {
			throw new UABlacklistedException();
		}
		
	}

}
