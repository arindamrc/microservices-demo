/**
 * 
 */
package com.adex.filterservice.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.adex.filterservice.domain.RequestCounts;
import com.adex.filterservice.domain.RequestStatistics;
import com.adex.filterservice.dto.Request;
import com.adex.filterservice.service.RequestStatisticsService;

/**
 * @author arc
 *
 */
@RestController
@RequestMapping("/stats")
public class RequestStatisticsController {

	private RequestStatisticsService requestStatisticsService;

	@Autowired
	public RequestStatisticsController(RequestStatisticsService requestStatisticsService) {
		this.requestStatisticsService = requestStatisticsService;
	}

	@GetMapping("/customer/{cid}")
	public ResponseEntity<RequestCounts> getRequestCountForCustomer(@PathVariable("cid") final Long cid) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForCustomer(cid));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}

	@GetMapping("/customer-day/{cid}/{date}")
	public ResponseEntity<RequestCounts> getRequestCountForCustomerForDay(
			@PathVariable("cid") final Long cid, 
			@PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy") final LocalDate date) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForCustomerForDay(cid, date));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}
	
	@GetMapping("/day/{date}")
	public ResponseEntity<RequestCounts> getRequestCountsForDay(
			@PathVariable("date") @DateTimeFormat(pattern = "dd-MM-yyyy")  final LocalDate date) {
		try {
			return ResponseEntity.of(requestStatisticsService.getRequestCountForDay(date));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}
	
	@PostMapping("/request")
	public ResponseEntity<RequestStatistics> logRequest(@Valid @RequestBody Request request) {
		try {
			return ResponseEntity.ok().body(requestStatisticsService.addRequest(request));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!", e);
		}
	}
}
