/**
 * 
 */
package com.adex.filterservice.service;

import java.time.LocalDate;
import java.util.Optional;

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
import com.adex.filterservice.dto.Request;
import com.adex.filterservice.exceptions.CustomerNotFoundException;

/**
 * A service for the request statistic counting.
 * 
 * @author arc
 *
 */
public interface RequestStatisticsService {
	

	/**
	 * This is the main service that performs logging a request to the database.
	 * The validity of the request is determined in this service.
	 * 
	 * @param requestStatistics
	 * @return the persisted request in the database
	 * @throws CustomerNotFoundException 
	 * @throws Exception 
	 */
	public RequestStatistics addRequest(Request request) throws CustomerNotFoundException, Exception;
	
	/**
	 * Get the total number of requests made by the customer with id {@code cid}.
	 * 
	 * @param cid
	 * @return
	 */
	public Optional<RequestCounts> getRequestCountForCustomer(Long cid);
	
	/**
	 * Get the total number of requests made by the customer with id {@code cid}
	 * on the day {@code date}.
	 * 
	 * @param cid
	 * @param date
	 * @return
	 */
	public Optional<RequestCounts> getRequestCountForCustomerForDay(Long cid, LocalDate date);
	
	/**
	 * Get total number of requests made by all customers for the day {@code date}.
	 * 
	 * @param date
	 * @return
	 */
	public Optional<RequestCounts> getRequestCountForDay(LocalDate date);
}
